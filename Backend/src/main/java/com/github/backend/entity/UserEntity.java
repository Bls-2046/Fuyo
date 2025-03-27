package com.github.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    public UserEntity() {
        this.id = UUID.randomUUID().toString();
    }

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "username", nullable = false, length = 64)
    private String username; // 用户名

    @Column(name = "password", nullable = false, length = 64)
    private String password; // 密码

    @Column(name = "name", nullable = false, length = 64)
    private String name; // 姓名

    @Column(name = "department", nullable = false, length = 64)
    private String department; // 年级专业

    @Column(name = "email", nullable = false, length = 64)
    private String email; // 邮箱

    @Column(name = "phone", nullable = false, length = 15)
    private String phone; // 电话号

    @Column(name = "avatar_url")
    private String avatarUrl; // 头像 Url

    @Column(name = "cookie", nullable = false)
    private String cookie;

    @Column(name = "nickname")
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