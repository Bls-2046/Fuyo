package com.github.fuyo.listener;

public class StartupTasks {
    private StartupTasks() {}

    public static void 	scheduler() {
        ScheduleListener.start();
    }
}
