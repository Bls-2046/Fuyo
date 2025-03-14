package com.github.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class GenericRequest {
    private Map<String, Object> data;
}
