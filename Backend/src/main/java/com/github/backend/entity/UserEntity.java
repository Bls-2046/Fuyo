package com.github.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 255)
    private String id;

    private String username; // 用户名
    private String password; // 密码
    private String name; // 姓名
    private String department; // 年级专业
    private String email; // 邮箱
    private String phone; // 电话号
    private String avatarUrl; // 头像 Url
    private String cookie;
    private String nickname; // 微信昵称

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userEntity")
    @JsonManagedReference
    private List<TabletimeEntity> tabletime;

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
                ", wechatName='" + nickname + '\'' +
                '}';
    }
}