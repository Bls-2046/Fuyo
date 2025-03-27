package com.github.backend.controller.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class BaseResponse<T> {
    private int status;
    private String message;
    private T data;
    private String path;
    private Instant timestamp = Instant.now();
    private String errorDetail;
}
