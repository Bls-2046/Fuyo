package com.github.backend.exception;

import com.github.backend.controller.util.ResponseStatus;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final ResponseStatus status;
    private final String detailMessage;

    public ApiException(ResponseStatus status) {
        this(status, status.getMessage());
    }

    public ApiException(ResponseStatus status, String detailMessage) {
        super(detailMessage);
        this.status = status;
        this.detailMessage = detailMessage;
    }
}
