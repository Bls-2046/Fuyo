package com.github.fuyo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Collections;

@Data
public class UserEntity {
    private static volatile UserEntity userInformation;

    public UserEntity() {
        this.tabletimelist = Collections.emptyList(); // 或者初始化为一个新的 ArrayList<>()
    }

    // 公共静态方法，提供全局访问点
    public static UserEntity getUser() {
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
    private List<Tabletime> tabletimelist;

    @Data
    @AllArgsConstructor
    public static class Tabletime {
        private String keyid;
        private String id;
        private int x;
        private int y;
        private String value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton class, cloning not allowed");
    }
}
