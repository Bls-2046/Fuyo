package com.github.backend.entity.mysql;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "dormitory")
public class DormitoryEntity {

    public DormitoryEntity() {
        this.dormitoryId = UUID.randomUUID();
    }

    @Id
    @Column(name = "dormitory_id", nullable = false)
    private UUID dormitoryId;

    @Column(name = "dorm_no", length = 50)
    private String dormNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @Column(name = "water_fee", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal waterFee;

    @Column(name = "electricity_fee", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal electricityFee;

    @Override
    public String toString() {
        return "DormitoryEntity{" +
                "dormitoryId=" + dormitoryId +
                ", dormNo='" + dormNo + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                ", waterFee=" + waterFee +
                ", electricityFee=" + electricityFee +
                '}';
    }
}