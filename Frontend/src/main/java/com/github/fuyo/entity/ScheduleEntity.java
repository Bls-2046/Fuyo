package com.github.fuyo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 日程安排
 */
@Data
@AllArgsConstructor
public class ScheduleEntity {
    private String title; // 日程标题
    private LocalDateTime dateTime; // 日程时间
    private LocalDateTime reminderDateTime; // 日程提醒时间 (处理完成计算后存入)
    private String description; // 日程具体内容
    private Boolean isReminderInClient;
}
