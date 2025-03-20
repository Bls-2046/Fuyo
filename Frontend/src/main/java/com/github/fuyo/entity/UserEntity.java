package com.github.fuyo.entity;

import com.github.fuyo.dto.TableTimeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Collections;

@Data
public class UserEntity {
    private static volatile UserEntity userInformation;

    public UserEntity() {
        this.tabletime = Collections.emptyList();
    }

    // 公共静态方法，提供全局访问点
    public static UserEntity getUserInformation() {
        if (userInformation == null) {
            synchronized (UserEntity.class) {
                if (userInformation == null) {
                    userInformation = new UserEntity();
                }
            }
        }
        return userInformation;
    }

    private String id;
    private String username;
    private String name;
    private String department;
    private String email;
    private String phone;
    private String avatarUrl;
    private String cookie;
    private List<Tabletime> tabletime;

    @Data
    @AllArgsConstructor
    public static class Tabletime {
        private String keyID;
        private String clazz;
        private int x;
        private int y;
        private int beginDay;
        private int endDay;
        private String WeekType;
        private String place;
        private int startWeek;
        private int finishWeek;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton class, cloning not allowed");
    }
}
