package com.github.fuyo.controller;

import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.ScheduleViewEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.model.ScheduleModel;
import com.github.fuyo.view.messagebox.ErrorMessageBox;
import com.github.fuyo.view.navigation.schedule.ScheduleDialogView;
import com.github.fuyo.view.navigation.schedule.ScheduleView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.fuyo.view.navigation.schedule.ScheduleDialogView.showDialog;

@Slf4j
public class ScheduleController {
    @Getter
    private final ScheduleView view;
    private final ScheduleModel model;

    private ScheduleViewEntity viewEntity;
    private List<ScheduleEntity> scheduleEntities;

    private Thread remindMonitor;
    private Thread executeMonitor;

    public ScheduleController(ScheduleView view, ScheduleModel model) {
        this.view = view;
        this.model = model;

        viewEntity = view.getViewEntity();

        // Test
        // showDialog(new ScheduleEntity("Test",LocalDateTime.now(),LocalDateTime.now(),"N/A"));

        // 刷新监听逻辑
        monitorAndRepaint();

        // 提交按钮逻辑
        JButton submitButton = viewEntity.getSubmitButton();

        submitButton.addActionListener(e -> {

            // 重取数据
            scheduleEntities = view.getScheduleEntities();

            try {

                int second = 0;

                // 提前提醒时间
                switch (viewEntity.getRemindWidget().getType()) {
                    case 1 -> {
                        // Day
                        second = Integer.parseInt(viewEntity.getRemindTime().getText()) * 86400;
                    }
                    case 2 -> {
                        // Hrs
                        second = Integer.parseInt(viewEntity.getRemindTime().getText()) * 3600;
                    }
                    case 3 -> {
                        // Min
                        second = Integer.parseInt(viewEntity.getRemindTime().getText()) * 60;
                    }
                }

                LocalDate date = LocalDate.of(
                        Integer.parseInt(viewEntity.getScheduleYear().getText()),
                        Integer.parseInt(viewEntity.getScheduleMonth().getText()),
                        Integer.parseInt(viewEntity.getScheduleDay().getText())
                );

                LocalTime time = LocalTime.of(
                        Integer.parseInt(viewEntity.getScheduleHour().getText()),
                        Integer.parseInt(viewEntity.getScheduleMinute().getText()));

                LocalDateTime result = LocalDateTime.of(date, time);

                ScheduleEntity newScheduleEntity = new ScheduleEntity(
                        viewEntity.getTitle().getText(),
                        result,
                        result.minusSeconds(second),
                        viewEntity.getContent().getText()
                );

                // 添加newScheduleEntity到list中, TODO: 发送请求到后端?
                scheduleEntities.add(newScheduleEntity);

                // 刷新渲染
                view.setScheduleEntities(scheduleEntities);
                view.repaintEDW();

                // 显示目前添加的Entity信息 (getType: 1:Day, 2:Hrs, 3:Min)
                log.info("{}, unit: {}",newScheduleEntity.toString(),viewEntity.getRemindWidget().getType());

                // 清空全部输入框
                view.clearInput();
            }
            catch (NumberFormatException ex) {
                ErrorMessageBox.showErrorBox("输入格式有误，请检查");
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });



    }

    // 删除事件按钮执行器
    public static void deleteScheduleEventClicked(ScheduleEntity schedule) {
        // TODO: 发送到后端进行删除

        // 删除Schedule按钮点击后触发
        log.info("Delete Schedule for {} Clicked" , schedule.getTitle());
    }

    // 动态刷新
    private void monitorAndRepaint() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // 1S检查一次时间
        executor.scheduleAtFixedRate(() -> {

            List<ScheduleEntity> schedule = UserEntity.getUserInformation().getSchedule();

            if (!schedule.isEmpty()) {
                scheduleEntities = schedule;
                view.setScheduleEntities(scheduleEntities);
                view.repaintEDW();
            }

        }, 0, 1000, TimeUnit.MILLISECONDS); // 1 SEC / CHECK
    }
}
