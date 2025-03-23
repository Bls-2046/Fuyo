package com.github.fuyo.controller;

import com.github.fuyo.model.ScheduleModel;
import com.github.fuyo.view.navigation.schedule.ScheduleView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleController {
    @Getter
    private final ScheduleView view;
    private final ScheduleModel model;

    public ScheduleController(ScheduleView view, ScheduleModel model) {
        this.view = view;
        this.model = model;

        // 监听

    }
}
