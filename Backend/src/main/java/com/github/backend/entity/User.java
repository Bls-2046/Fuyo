package com.github.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String department;
    private String email;
    private String phone;
    private String cookie;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
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
                ", cookie='" + cookie + '\'' +
                '}';
    }

    @Data
    @Entity
    @Table(name = "tabletime")
    public static class Tabletime {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String keyid;
        private int x;
        private int y;
        private String value;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private User user;

        @Override
        public String toString() {
            return "Tabletime{" +
                    "id=" + id +
                    ", keyid='" + keyid + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}