package com.github.dto.dormitory;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class FetchDormitoryResponse {
    private int status;
    private String message;
    private Dormitory dormitory;

    @Data
    @Accessors(chain = true)
    public static class Dormitory {
        private UUID dormitoryId;
        private String dormNo;
        private BigDecimal waterFee;
        private BigDecimal electricityFee;
    }
}
