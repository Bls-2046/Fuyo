package com.github.backend.listener;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

public class ScheduleListener {
    // 当前时间, 每秒更新一次
    private static LocalDateTime now;

    private ScheduleListener() {}

    // 每秒检查所有事务,
    @Scheduled(fixedRate = 1000)
    private void checkAllScheduled() {

    }

    // 更新当前时间
    @Scheduled(fixedRate = 1000)
    private static void updateTimer() {
        ScheduleListener.now = LocalDateTime.now();
    }
}
