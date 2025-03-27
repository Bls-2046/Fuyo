package com.github.fuyo.listener;

import com.github.fuyo.dto.schedule.MarkRemindedScheduleForClientRequest;
import com.github.fuyo.dto.schedule.MarkRemindedScheduleForClientResponse;
import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.model.ScheduleModel;
import com.github.fuyo.utils.https.Https;
import com.github.fuyo.view.navigation.schedule.ScheduleDialogView;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.fuyo.view.navigation.schedule.ScheduleDialogView.showDialog;

@Slf4j
public final class ScheduleListener {
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private static final ExecutorService taskExecutor = Executors.newCachedThreadPool();

    private static boolean isRunning = false; // 监听状态标志

    // 私有构造方法，防止外部实例化
    private ScheduleListener() {}

    /**
     * 登录成功后调用此方法启动监听
     */
    public static synchronized void start() {
        if (isRunning) {
            return;
        }
        executor.scheduleAtFixedRate(() -> {
            log.info("ScheduleListener is running");
            try {
                checkAndTriggerSchedules();
            } catch (Exception e) {
                e.printStackTrace();
                log.info(e.getMessage());
            }
        }, 0, 1, TimeUnit.SECONDS);

        isRunning = true;
    }

    /**
     * 检查日程并提醒
     */
    private static void checkAndTriggerSchedules() {
        LocalDateTime now = LocalDateTime.now();
        for (ScheduleEntity schedule : (UserEntity.getUserInformation().getSchedule())) {
            if (!schedule.getIsReminderInClient()) {
                if (now.isAfter(schedule.getReminderDateTime()) && now.isBefore(schedule.getDateTime())) {

                    markReminderScheduleForClient(schedule);
                    log.warn(schedule.toString());

                    schedule.setIsReminderInClient(Boolean.TRUE);

                    taskExecutor.submit(() -> {
                        // 消息框弹出
                        ScheduleDialogView.showDialog(schedule);
                    });
                }
            }
        }
        ScheduleModel.getSchedule(UserEntity.getUserInformation().getUsername());
    }

    /**
     * 想后端发送请求改变数据库标记
     * @param schedule 日程信息
     */
    private static void markReminderScheduleForClient(ScheduleEntity schedule) {
        try {
            String url = "http://127.0.0.1:8080/api/update/schedule/mark-reminder-for-client";
            MarkRemindedScheduleForClientRequest
                    markRemindedScheduleForClientRequest = new MarkRemindedScheduleForClientRequest();

            markRemindedScheduleForClientRequest.setUsername(
                    UserEntity.getUserInformation().getUsername()
            );
            markRemindedScheduleForClientRequest.setOpenid(
                    UserEntity.getUserInformation().getWechatUser().getOpenid()
            );
            markRemindedScheduleForClientRequest.setSchedule(schedule);

            MarkRemindedScheduleForClientResponse markRemindedScheduleForClientResponse
                    = Https.post(
                            url,
                    markRemindedScheduleForClientRequest,
                    null,
                    MarkRemindedScheduleForClientResponse.class);

            if (markRemindedScheduleForClientResponse.getStatus() == 200) {
                log.info("mark reminder for client success");
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 停止监听（如退出登录时调用）
     */
    public static synchronized void stop() {
        executor.shutdownNow(); // 立即终止任务
        isRunning = false;
    }
}