package com.github.backend.exception;

import com.github.backend.controller.util.BaseResponse;
import com.github.backend.controller.util.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<BaseResponse<?>> handleApiException(ApiException ex, WebRequest request) {
        log.warn("API Exception: {}", ex.getMessage());
        return buildErrorResponse(ex.getStatus(), ex.getDetailMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        String errorMsg = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("Validation error: {}", errorMsg);
        return buildErrorResponse(ResponseStatus.BAD_REQUEST, errorMsg, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleUnknownException(Exception ex, WebRequest request) {
        log.error("Unexpected error: ", ex);
        return buildErrorResponse(ResponseStatus.SERVER_ERROR,
                "An unexpected error occurred", request);
    }

    private ResponseEntity<BaseResponse<?>> buildErrorResponse(
            ResponseStatus status, String message, WebRequest request) {
        BaseResponse<?> response = new BaseResponse<>()
                .setStatus(status.getCode())
                .setMessage(message)
                .setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        if (isDevEnvironment()) {
            response.setErrorDetail(message);
        }

        return ResponseEntity.status(status.getCode()).body(response);
    }

    private boolean isDevEnvironment() {
        // 实现环境检测逻辑
        return true;
    }
}
