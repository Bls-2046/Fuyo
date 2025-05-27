package com.github.fuyo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DormitoryEntity {
    private String dormNo;
    private double waterFee;
    private double electricityFee;
}
