package com.github.backend.exception;

import com.github.backend.controller.util.ResponseStatus;

public class NotFoundException extends ApiException {
    public NotFoundException() {
        super(ResponseStatus.NOT_FOUND);
    }

    public NotFoundException(String detailMessage) {
        super(ResponseStatus.NOT_FOUND, detailMessage);
    }
}
