package com.github.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * 用户课表
 */
@Data
@Entity
@Table(name = "tabletime")
public class TabletimeEntity {
    public TabletimeEntity() {
        this.id = UUID.randomUUID().toString();
    }

    @Id
    @Column(name = "keyID", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "clazz", nullable = false)
    private String clazz; // 课程名

    @Column(name = "x", nullable = false)
    private int x;

    @Column(name = "y", nullable = false)
    private int y;

    @Column(name = "begin_day", nullable = false)
    private int beginDay; // 当天课程开始时间

    @Column(name = "end_day", nullable = false)
    private int endDay; // 当天课程结束时间

    @Column(name = "week_type", nullable = false)
    private String weekType; // 是否分单双周

    @Column(name = "place", nullable = false)
    private String place; // 上课地点

    @Column(name = "start_week", nullable = false)
    private int startWeek; // 本学期开始本课程上课时间

    @Column(name = "finish_week", nullable = false)
    private int finishWeek; // 本学期结束本课程上课时间

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity userEntity;

    @Override
    public String toString() {
        return "Tabletime{" +
                "keyDID=" + id +
                ", clazz='" + clazz +
                ", x=" + x +
                ", y=" + y +
                ", beginDay='" + beginDay + '\'' +
                ", endDay='" + endDay + '\'' +
                ", weekType='" + weekType + '\'' +
                ", place='" + place + '\'' +
                ", startWeek='" + startWeek + '\'' +
                ", finishWeek='" + finishWeek + '\'' +
                '}';
    }
}