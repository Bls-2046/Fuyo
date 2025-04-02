package com.github.backend.entity.mysql;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Yiyan")
public class YiyanEntity {
    public YiyanEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 25)
    private String sentence;
}
