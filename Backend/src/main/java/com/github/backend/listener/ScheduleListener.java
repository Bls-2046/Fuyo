package com.github.backend.listener;

import com.github.backend.entity.ScheduleEntity;
import com.github.backend.repository.ScheduleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于处理日程发送
 */

@Component
public class ScheduleListener {
    private static List<ScheduleEntity> scheduleList  = new ArrayList<>(); // 保存当前所有的日程信息

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
                if (now.equals(schedule.getReminderDatetime())) {
                    reminderExecutorService.execute(() -> {
                        // 提醒逻辑
                    });
                }
            }
        });
    }

    /**
     * 当有新的日志被添加时更新 scheduleList
     */
    public void updateScheduleList() {
        scheduleList = scheduleRepository.findAll();
    }

    @PreDestroy
    public void cleanup() {
        checkExecutorService.shutdown();
        reminderExecutorService.shutdown();
    }
}
