package com.github.fuyo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日程安排
 */
@Data
public class ScheduleEntity {
    private String title; // 日程标题
    private LocalDateTime datetime; // 日程时间
    private LocalDateTime reminderDatetime; // 日程提醒时间 (处理完成计算后存入)
    private String description; // 日程具体内容
}
