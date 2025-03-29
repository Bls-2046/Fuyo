package com.github.fuyo.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class LoginResponse {
    private int status;
    private String message;
    private String timestamp;
}
