package com.github.backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private int status;
    private String message;
    private String id;
}
