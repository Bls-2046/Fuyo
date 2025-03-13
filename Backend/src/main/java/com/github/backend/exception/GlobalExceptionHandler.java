package com.github.backend.exception;

import com.github.backend.model.Response;
import com.github.backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
