package com.github.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Map;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tabletime_id", unique = true)
    private Tabletime tabletime;

    // 静态内部类
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

        @ElementCollection
        @CollectionTable(name = "valuelist", joinColumns = @JoinColumn(name = "tabletime_id"))
        @MapKeyColumn(name = "map_key") // 避免使用 MySQL 关键字
        @Column(name = "value")
        private Map<String, String> valuelist;

        @OneToOne(mappedBy = "tabletime")
        private User user;
    }
}