package com.github.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Tabletime> tabletimelist;

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

    @Data
    @Entity
    @Table(name = "tabletime")
    public static class Tabletime {
        @Id
        @Column(name = "keyid", unique = true, nullable = false, length = 36) // UUID is 36 characters long
        private String keyid;

        @Column(name = "id", nullable = false)
        private String id;

        private int x;
        private int y;
        private String value;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private UserEntity userEntity;

        public Tabletime() {
            this.keyid = UUID.randomUUID().toString();
        }

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
