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
    @Id
    @Column(name = "keyID", unique = true, nullable = false, length = 36)
    private String keyID;

    private String clazz; // 课程名
    private int x;
    private int y;
    private int beginDay; // 当天课程开始时间
    private int endDay; // 当天课程结束时间
    private String weekType; // 是否分单双周
    private String place; // 上课地点
    private int startWeek; // 本学期开始本课程上课时间
    private int finishWeek; // 本学期结束本课程上课时间

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity userEntity;

    public TabletimeEntity() {
        this.keyID = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Tabletime{" +
                "keyDID=" + keyID +
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