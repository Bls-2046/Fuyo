package com.github.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 日程安排
 */
@Data
@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    private String openid; // 微信公众号用户的唯一标识
    private String title; // 日程标题
    private LocalDateTime datetime; // 日程时间
    private LocalDateTime reminderDatetime; // 日程提醒时间 (处理完成计算后存入)
    private String description; // 日程具体内容

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity userEntity;

    // 生成唯一标识
    public ScheduleEntity() {
        this.id = UUID.randomUUID().toString();
    }
}
