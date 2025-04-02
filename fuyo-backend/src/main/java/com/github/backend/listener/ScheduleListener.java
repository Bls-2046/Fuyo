package com.github.backend.listener;

import com.github.backend.entity.mysql.ScheduleEntity;
import com.github.backend.repository.mysql.ScheduleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于日程的监听
 */
@Slf4j
@Component
public class ScheduleListener {
    private static List<ScheduleEntity> scheduleList  = new CopyOnWriteArrayList<>(); // 保存当前所有的日程信息

    private final ExecutorService checkExecutorService = Executors.newCachedThreadPool(); // 创建线程池用于检查日志
    private final ExecutorService reminderExecutorService = Executors.newCachedThreadPool(); // 创建线程池用于提醒日志

    private final ScheduleRepository scheduleRepository;

    private ScheduleListener(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @PostConstruct
    public void init() {
        updateScheduleList();
    }

    // 每秒检查所有事务,
    @Scheduled(fixedRate = 1000)
    private void checkAllScheduled() {
        // 对所有日程进行检查
        checkExecutorService.execute(() -> {
            LocalDateTime now = LocalDateTime.now();
            for(ScheduleEntity schedule : scheduleList) {
                // 如果当前时间与提醒时间一致, 将该事务保存至线程池并等待发送提醒
                // reminderDateTime <= now <= DateTime
                if (!now.isBefore(schedule.getReminderDateTime()) && !now.isAfter(schedule.getDateTime())) {
                    reminderExecutorService.execute(() -> {
                        // 提醒逻辑
                        Boolean isSendWeChatMessage = null;

                        if (isSendWeChatMessage) {
                            scheduleRepository.delete(schedule);
                        }
                    });
                }
            }
        });
    }

    /**
     *   检查列表内的所有时间
     *   若提醒时间超过现在的时间, 删除本条日程信息
     */
    @Scheduled(fixedRate = 1000)
    private void checkScheduleReminderDatetime() {
        LocalDateTime now = LocalDateTime.now(); // 获取当前时间
        for(ScheduleEntity schedule : scheduleList) {
            LocalDateTime reminderTimePlus1800s = schedule.getReminderDateTime().plusSeconds(1800); // 超过 30 分钟
            if (now.equals(reminderTimePlus1800s)) {
                scheduleRepository.delete(schedule);
            }
        }
    }

    /**
     * 监听 ScheduleEntity 的保存事件
     */
    @TransactionalEventListener
    private void handleScheduleSave(ScheduleEntity schedule) {
        System.out.println("Schedule saved: " + schedule.getId());
        updateScheduleList();
    }

    /**
     * 监听 ScheduleEntity 的删除事件
     */
    @TransactionalEventListener
    private void handleScheduleDelete(ScheduleEntity schedule) {
        System.out.println("Schedule deleted: " + schedule.getId());
        updateScheduleList();
    }

    /**
     * 当有新的日志被添加时更新 scheduleList
     */
    private void updateScheduleList() {
        List<ScheduleEntity> newScheduleList = scheduleRepository.findAll();

        // 按提醒日期进行排序
        newScheduleList.sort(Comparator.comparing(
                ScheduleEntity::getReminderDateTime,
                Comparator.nullsFirst(Comparator.naturalOrder())
        ));

        scheduleList.clear();
        scheduleList.addAll(newScheduleList);
    }

    /**
     * 退出程序时销毁线程
     */
    @PreDestroy
    private void cleanup() {
        checkExecutorService.shutdown();
        reminderExecutorService.shutdown();
    }
}
