package com.github.fuyo.listener;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartupTasks {
    private StartupTasks() {}

    public static void 	scheduler() {
        log.info("监听器启动中");
        ScheduleListener.start();
        log.info("监听器启动完成");
    }
}
