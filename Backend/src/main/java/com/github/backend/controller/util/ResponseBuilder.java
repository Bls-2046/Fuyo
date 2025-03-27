package com.github.backend.controller.util;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

/**
 * 响应构建工具类，支持所有标准HTTP状态码
 */
@UtilityClass
public class ResponseBuilder {
    // Default messages
    private static final String DEFAULT_SUCCESS_MSG = "Request processed successfully";
    private static final String DEFAULT_CREATED_MSG = "Resource created successfully";
    private static final String DEFAULT_ACCEPTED_MSG = "Request accepted for processing";
    private static final String DEFAULT_NO_CONTENT_MSG = "No content available";
    private static final String DEFAULT_BAD_REQUEST_MSG = "Invalid request parameters";
    private static final String DEFAULT_UNAUTHORIZED_MSG = "Authentication required";
    private static final String DEFAULT_FORBIDDEN_MSG = "Access denied";
    private static final String DEFAULT_NOT_FOUND_MSG = "Resource not found";
    private static final String DEFAULT_CONFLICT_MSG = "Resource conflict detected";
    private static final String DEFAULT_SERVER_ERROR_MSG = "Internal server error";

    public static BaseResponse<Void> unauthorized(String message) {
        return new BaseResponse<Void>()
                .setStatus(ResponseStatus.UNAUTHORIZED.getCode())
                .setMessage(message);
    }

    public static BaseResponse<Void> forbidden(String message) {
        return new BaseResponse<Void>()
                .setStatus(ResponseStatus.FORBIDDEN.getCode())
                .setMessage(message);
    }

    public static BaseResponse<Void> serverError(String message) {
        return new BaseResponse<Void>()
                .setStatus(ResponseStatus.SERVER_ERROR.getCode())
                .setMessage(message);
    }

    // ==================== 2xx Success ====================

    public static BaseResponse<Void> ok(String message) {
        return new BaseResponse<Void>()
                .setStatus(ResponseStatus.SUCCESS.getCode())
                .setMessage(message);
    }

    public static <R extends BaseResponse<Void>> R ok(R response, String message) {
        response.setStatus(ResponseStatus.SUCCESS.getCode());
        response.setMessage(message);
        return response;
    }

    public static <T, R extends BaseResponse<T>> R ok(R response, T data) {
        return ok(response, data, DEFAULT_SUCCESS_MSG);
    }

    public static <T, R extends BaseResponse<T>> R ok(R response, T data, String message) {
        response.setStatus(ResponseStatus.SUCCESS.getCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T, R extends BaseResponse<T>> R created(R response, T data) {
        return created(response, data, DEFAULT_CREATED_MSG);
    }

    public static <T, R extends BaseResponse<T>> R created(R response, T data, String message) {
        response.setStatus(ResponseStatus.CREATED.getCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T, R extends BaseResponse<T>> R accepted(R response, T data) {
        return accepted(response, data, DEFAULT_ACCEPTED_MSG);
    }

    public static <T, R extends BaseResponse<T>> R accepted(R response, T data, String message) {
        response.setStatus(ResponseStatus.ACCEPTED.getCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T, R extends BaseResponse<T>> R noContent(R response) {
        return noContent(response, DEFAULT_NO_CONTENT_MSG);
    }

    public static <T, R extends BaseResponse<T>> R noContent(R response, String message) {
        response.setStatus(ResponseStatus.NO_CONTENT.getCode());
        response.setMessage(message);
        return response;
    }

    // ==================== 4xx Client Errors ====================

    public static <T, R extends BaseResponse<T>> R badRequest(R response) {
        return badRequest(response, DEFAULT_BAD_REQUEST_MSG);
    }

    public static <T, R extends BaseResponse<T>> R badRequest(R response, String message) {
        response.setStatus(ResponseStatus.BAD_REQUEST.getCode());
        response.setMessage(message);
        return response;
    }

    public static <T, R extends BaseResponse<T>> R unauthorized(R response) {
        return unauthorized(response, DEFAULT_UNAUTHORIZED_MSG);
    }

    public static <T, R extends BaseResponse<T>> R unauthorized(R response, String message) {
        response.setStatus(ResponseStatus.UNAUTHORIZED.getCode());
        response.setMessage(message);
        return response;
    }

    public static <T, R extends BaseResponse<T>> R forbidden(R response) {
        return forbidden(response, DEFAULT_FORBIDDEN_MSG);
    }

    public static <T, R extends BaseResponse<T>> R forbidden(R response, String message) {
        response.setStatus(ResponseStatus.FORBIDDEN.getCode());
        response.setMessage(message);
        return response;
    }

    public static <T, R extends BaseResponse<T>> R notFound(R response) {
        return notFound(response, DEFAULT_NOT_FOUND_MSG);
    }

    public static <T, R extends BaseResponse<T>> R notFound(R response, String message) {
        response.setStatus(ResponseStatus.NOT_FOUND.getCode());
        response.setMessage(message);
        return response;
    }

    public static <T, R extends BaseResponse<T>> R conflict(R response) {
        return conflict(response, DEFAULT_CONFLICT_MSG);
    }

    public static <T, R extends BaseResponse<T>> R conflict(R response, String message) {
        response.setStatus(ResponseStatus.CONFLICT.getCode());
        response.setMessage(message);
        return response;
    }

    // ==================== 5xx Server Errors ====================

    public static <T, R extends BaseResponse<T>> R serverError(R response) {
        return serverError(response, DEFAULT_SERVER_ERROR_MSG);
    }

    public static <T, R extends BaseResponse<T>> R serverError(R response, String message) {
        response.setStatus(ResponseStatus.SERVER_ERROR.getCode());
        response.setMessage(message);
        return response;
    }

    // ==================== Utility Methods ====================

    public static <T, R extends BaseResponse<T>> R auto(R response, Supplier<T> supplier) {
        return auto(response, supplier, DEFAULT_SUCCESS_MSG, DEFAULT_NOT_FOUND_MSG);
    }

    public static <T, R extends BaseResponse<T>> R auto(
            R response, Supplier<T> supplier, String successMsg, String notFoundMsg) {
        try {
            T data = supplier.get();
            if (data != null) {
                return ok(response, data, successMsg);
            }
            return notFound(response, notFoundMsg);
        } catch (Exception e) {
            return serverError(response, e.getMessage());
        }
    }
}
