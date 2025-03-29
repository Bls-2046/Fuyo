package com.github.fuyo.controller;

import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.ScheduleViewEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.model.ScheduleModel;
import com.github.fuyo.view.messagebox.ErrorMessageBox;
import com.github.fuyo.view.navigation.schedule.ScheduleView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
public class ScheduleController {
    @Getter
    private final ScheduleView view;
    @Getter
    private final ScheduleModel model;

    private ScheduleViewEntity viewEntity;
    private List<ScheduleEntity> scheduleEntities;

    public ScheduleController(ScheduleView view, ScheduleModel model) {
        this.view = view;
        this.model = model;

        // 时间设为当前时间
        updateNowDateTime(this.view);

        viewEntity = view.getViewEntity();

        // 刷新监听逻辑
        monitorAndRepaint();

        // 提交按钮逻辑
        JButton submitButton = viewEntity.getSubmitButton();

        submitButton.addActionListener(e -> {

            // 重取数据
            scheduleEntities = view.getScheduleEntities();

            try {

                int second = 0;

                // 提前提醒时间, 通过日期时间计算提醒时间
                switch (viewEntity.getRemindWidget().getType()) {
                    case 1 -> // Day
                            second = Integer.parseInt(viewEntity.getRemindTime().getText()) * 86400;
                    case 2 -> // Hrs
                            second = Integer.parseInt(viewEntity.getRemindTime().getText()) * 3600;
                    case 3 -> // Min
                            second = Integer.parseInt(viewEntity.getRemindTime().getText()) * 60;
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
                        "",
                        viewEntity.getTitle().getText(),
                        result,
                        result.minusSeconds(second),
                        viewEntity.getContent().getText(),
                        false
                );

                String addResult = model.addSchedule(newScheduleEntity);

                switch (addResult) {
                    case "添加成功" -> {
                        ScheduleModel.fetchSchedule(UserEntity.getUserInformation().getUsername());

                        scheduleEntities.add(newScheduleEntity);

                        // 刷新渲染
                        view.setScheduleEntities(scheduleEntities);
                        view.repaintEDW();

                        // 显示目前添加的Entity信息 (getType: 1:Day, 2:Hrs, 3:Min)
                        log.info("{}, unit: {}", newScheduleEntity, viewEntity.getRemindWidget().getType());

                        updateNowDateTime(this.view); // 更新时间输入框为当前时间
                    }
                    case null, default -> ErrorMessageBox.showErrorBox(addResult);
                }
            }
            catch (DateTimeException dtex) {
                view.clearInput();
                ErrorMessageBox.showErrorBox("标题及日期不能为空, 请重新输入");
            }
            catch (Exception ex) {
                updateNowDateTime(this.view);
                ErrorMessageBox.showErrorBox("添加失败");
                log.error(ex.getMessage());
            }
        });
    }

    /**
     * 更新输入框默认数据
     * @param view 日程视图
     */
    public void updateNowDateTime(ScheduleView view) {
        // 设置默认标题
        view.getViewEntity().getTitle().setText("我的日程");
        // 设置默认内容
        view.getViewEntity().getContent().setText("好像有什么重要的事...");
        // 将时间设置设为默认值
        view.getViewEntity().getRemindTime().setText("0");
        LocalDateTime now = LocalDateTime.now();
        view.getViewEntity().getScheduleYear().setText(String.valueOf(now.getYear()));
        view.getViewEntity().getScheduleMonth().setText(String.valueOf(now.getMonthValue()));
        view.getViewEntity().getScheduleDay().setText(String.valueOf(now.getDayOfMonth()));
        view.getViewEntity().getScheduleHour().setText(String.valueOf(now.getHour()));
        view.getViewEntity().getScheduleMinute().setText(String.valueOf(now.getMinute()));
    }

    /**
     * 删除事件按钮执行器
     * @param schedule 用户指定日程信息
     */
    public static void deleteScheduleEventClicked(ScheduleEntity schedule) {

        ScheduleModel.deleteSchedule(schedule);

        // 删除Schedule按钮点击后触发
        log.info("删除日程标题为: “{}”的日程信息" , schedule.getTitle());
    }

    /**
     * 点击添加按钮后刷新
     */
    private void monitorAndRepaint() {
        List<ScheduleEntity> schedule = UserEntity.getUserInformation().getSchedule();

        if (!schedule.isEmpty()) {
            scheduleEntities = schedule;
            view.setScheduleEntities(scheduleEntities);
            view.repaintEDW();
        }
    }
}
