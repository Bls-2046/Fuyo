package com.github.dto.dormitory;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class FetchDormitoryResponse {
    private int status;
    private String message;
    private Dormitory dormitory;

    @Data
    @Accessors(chain = true)
    public static class Dormitory {
        private String dormNo;
        private double waterFee;
        private double electricityFee;
    }
}
