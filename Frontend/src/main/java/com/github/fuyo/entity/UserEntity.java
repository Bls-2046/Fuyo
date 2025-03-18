package com.github.fuyo.entity;

import java.util.List;
public class UserEntity {
    private String id;
    private String username;
    private String name;
    private String department;
    private String email;
    private String phone;
    private String avatarUrl;
    private String cookie;
    private List<Tabletime> tabletimelist;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", cookie='" + cookie + '\'' +
                '}';
    }

    public static class Tabletime {
        private String keyid;
        private String id;
        private int x;
        private int y;
        private String value;

        @Override
        public String toString() {
            return "Tabletime{" +
                    "key_id=" + keyid +
                    "id=" + id +
                    ", x=" + x +
                    ", y=" + y +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
