package com.github.fuyo.listener;

import com.github.dto.schedule.MarkRemindedScheduleForClientRequest;
import com.github.dto.schedule.MarkRemindedScheduleForClientResponse;
import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.Https;
import com.github.fuyo.view.navigation.schedule.ScheduleDialogView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.*;

@Slf4j
public final class ScheduleListener {

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static final ExecutorService taskExecutor = Executors.newCachedThreadPool();
    private static ScheduledFuture<?> scheduledFuture;
    private static volatile boolean isRunning = false;

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
            try {
                checkAndTriggerSchedules();
            } catch (Exception e) {
                log.error(e.getMessage());
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
                if (now.isAfter(schedule.getReminderDateTime())) {

                    markReminderScheduleForClient(schedule);
                    log.info("显示提示弹窗的日程为: {}", schedule);

                    schedule.setIsReminderInClient(Boolean.TRUE);

                    taskExecutor.submit(() -> {
                        // 消息框弹出
                        ScheduleDialogView.showDialog(schedule);
                    });
                }
            }
        }
    }

    /**
     * 向后端发送请求改变数据库标记
     * @param schedule 日程信息
     */
    private static void markReminderScheduleForClient(ScheduleEntity schedule) {
        try {
            String url = "http://127.0.0.1:8080/api/update/schedule/mark-reminder-for-client";
            MarkRemindedScheduleForClientRequest
                    markRemindedScheduleForClientRequest = new MarkRemindedScheduleForClientRequest();

            markRemindedScheduleForClientRequest.setId(schedule.getId());
            markRemindedScheduleForClientRequest.setUsername(UserEntity.getUserInformation().getUsername());

            MarkRemindedScheduleForClientResponse markRemindedScheduleForClientResponse
                    = Https.put(url, markRemindedScheduleForClientRequest, null, MarkRemindedScheduleForClientResponse.class);

            if (markRemindedScheduleForClientResponse.getStatus() == HttpStatus.OK.value()) {
                log.info("mark reminder for client success");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停监听，并清空所有待执行和正在执行的任务
     */
    public static synchronized void pause() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true); // true表示中断正在执行的任务
        }
        // 清空线程池的任务队列
        ((ScheduledThreadPoolExecutor) executor).getQueue().clear();
        isRunning = false;
    }

    /**
     * 完全停止监听（退出登录时调用）
     */
    public static synchronized void stop() {
        pause(); // 先暂停任务
        executor.shutdownNow(); // 彻底关闭线程池（仅限程序退出时调用）
    }
}