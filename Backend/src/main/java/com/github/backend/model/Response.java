package com.github.backend.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private Integer status;
    private String message;
    private T data;

    // 快速返回操作成功的响应结果(带响应数据)
    @NotNull
    @Contract("_ -> new")
    public static <E> Response <E> success(E data) {
        return new Response<>(200, "请求成功: 带参", data);
    }

    // 快速返回操作成功的响应结构(不带响应参数)
    @NotNull
    @Contract(" -> new")
    public static Response success() {
        return new Response(200, "请求成功: 不带参", null);
    }

    @NotNull
    @Contract("_ -> new")
    public static Response error(String message) {
        return new Response(402, message, null);
    }
}
