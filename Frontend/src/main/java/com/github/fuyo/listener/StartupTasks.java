package com.github.fuyo.listener;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartupTasks {
    private StartupTasks() {}

    public static void 	scheduler() {
        log.info("Schedule Listener 日程监听器启动中...");
        ScheduleListener.start();
        log.info("Schedule Listener 日程监听开始运行!");
    }
}
