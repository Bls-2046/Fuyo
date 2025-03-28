package com.github.fuyo.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class LoginResponse {
    private int status;
    private String message;
    private String timestamp; // 字段名与JSON完全匹配

    // 可选：添加日期时间转换方法
    public ZonedDateTime getTimestampAsDate() {
        return ZonedDateTime.parse(timestamp);
    }
}
