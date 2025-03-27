package com.github.backend.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)  // 确保支持链式调用
public class LoginResponse {
    private int status;
    private String message;
    private Instant timestamp = Instant.now();

    // 必须显式添加setter方法（虽然@Data会生成，但有些IDE需要显式声明）
    public LoginResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public LoginResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
