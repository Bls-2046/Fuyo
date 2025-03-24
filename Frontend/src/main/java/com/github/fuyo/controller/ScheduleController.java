package com.github.fuyo.controller;

import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.ScheduleViewEntity;
import com.github.fuyo.model.ScheduleModel;
import com.github.fuyo.view.navigation.schedule.ScheduleView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
public class ScheduleController {
    @Getter
    private final ScheduleView view;
    private final ScheduleModel model;

    private ScheduleViewEntity viewEntity;
    private List<ScheduleEntity> scheduleEntities;

    public ScheduleController(ScheduleView view, ScheduleModel model) {
        this.view = view;
        this.model = model;

        viewEntity = view.getViewEntity();

        // 监听

        // 提交按钮逻辑
        JButton submitButton = viewEntity.getSubmitButton();

        submitButton.addActionListener(e -> {

            // 重取数据
            scheduleEntities = view.getScheduleEntities();

            // 新增数据 (请改成需要的逻辑)
            try {
                LocalDate date = LocalDate.of(
                        Integer.parseInt(viewEntity.getScheduleYear().getText()),
                        Integer.parseInt(viewEntity.getScheduleMonth().getText()),
                        Integer.parseInt(viewEntity.getScheduleDay().getText())
                );

                LocalTime time = LocalTime.of(
                        Integer.parseInt(viewEntity.getScheduleHour().getText()),
                        Integer.parseInt(viewEntity.getScheduleMinute().getText()));

                ScheduleEntity newScheduleEntity = new ScheduleEntity(
                        viewEntity.getTitle().getText(),
                        LocalDateTime.of(date, time),
                        null, // TODO: 请改成实际的逻辑
                        viewEntity.getContent().getText()
                );

                scheduleEntities.add(newScheduleEntity);

                // 刷新渲染
                view.setScheduleEntities(scheduleEntities);
                view.repaintEDW();

                // 显示目前添加的Entity信息 (getType: 1:Day, 2:Hrs, 3:Min)
                log.info("{}, unit: {}",newScheduleEntity.toString(),viewEntity.getRemindWidget().getType());

                // 清空全部输入框
                view.clearInput();
            } catch (Exception ex) {

                // TODO: 显示错误窗口(ErrorMessageBox)
                ex.printStackTrace();
                log.error(ex.getMessage() + "解析错误?");

            }
        });

    }

    // 删除事件按钮执行器
    public static void deleteScheduleEventClicked(ScheduleEntity schedule) {
        // 删除Schedule按钮点击后触发
        log.info("Delete Schedule for {} Clicked" , schedule.getTitle());
    }
}
