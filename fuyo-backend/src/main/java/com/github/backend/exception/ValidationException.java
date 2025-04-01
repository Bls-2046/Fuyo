package com.github.backend.exception;

import com.github.backend.controller.util.ResponseStatus;

public class ValidationException extends ApiException {
    public ValidationException(String detailMessage) {
        super(ResponseStatus.BAD_REQUEST, detailMessage);
    }
}
