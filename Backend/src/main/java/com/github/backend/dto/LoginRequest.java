package com.github.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
