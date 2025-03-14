package com.github.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class GenericResponse {
    private int status;
    private String message;
    private Map<String, Object> data;
}
