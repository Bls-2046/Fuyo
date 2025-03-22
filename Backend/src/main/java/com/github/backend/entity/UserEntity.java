package com.github.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 255)
    private String id;

    private String username;
    private String password;
    private String name;
    private String department;
    private String email;
    private String phone;
    private String avatarUrl;
    private String cookie;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userEntity")
    @JsonManagedReference
    private List<Tabletime> tabletime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", cookie='" + cookie + '\'' +
                '}';
    }

    /**
     * 用户课表
     */
    @Data
    @Entity
    @Table(name = "tabletime")
    public static class Tabletime {
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

        public Tabletime() {
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

    /**
     * 日程安排
     */
    @Data
    @Entity
    @Table(name = "schedule")
    public static class ScheduleEntity {
        @Id
        @Column(name = "id", unique = true, nullable = false, length = 36)
        private String id;

        private String eventTitle; // 事务标题
        private LocalDateTime dateTime; // 事务提醒时间
        private int earlyTime; // 提前提醒时间, 默认 15 分钟
        private String earlyTimeType; // 提前提醒的时间类型, 默认 分钟
        private String eventDescription; // 事务具体内容描述

        public ScheduleEntity() {
            this.id = UUID.randomUUID().toString();
        }

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private UserEntity userEntity;

        @Override
        public String toString() {
            return "Schedule{" +
                    "id=" + id +
                    ", eventTitle='" + eventTitle +
                    ", dateTime=" + dateTime +
                    ", earlyTime=" + earlyTime +
                    ", earlyTimeType='" + earlyTimeType + '\'' +
                    ", eventDescription='" + eventDescription + '\'' +
                    '}';
        }
    }
}