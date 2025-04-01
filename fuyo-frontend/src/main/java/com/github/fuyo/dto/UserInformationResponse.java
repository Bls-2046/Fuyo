package com.github.fuyo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserInformationResponse {
    public String status;
    public String message;
    public Map<String, String> data;
}
