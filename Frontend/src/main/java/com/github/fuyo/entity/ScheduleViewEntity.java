package com.github.fuyo.entity;

import com.github.fuyo.view.navigation.schedule.ReminderWidget;
import lombok.*;

import javax.swing.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleViewEntity {

    private JTextField title;
    private JTextArea content;
    private JTextField remindTime;
    private ReminderWidget remindWidget; // 1 = Day, 2 = Hrs, 3 = Min
    private JTextField scheduleYear;
    private JTextField scheduleMonth;
    private JTextField scheduleDay;
    private JTextField scheduleHour;
    private JTextField scheduleMinute;

    // Button
    private JButton submitButton;

}
