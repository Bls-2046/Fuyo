package com.github.backend.service;

import com.github.backend.entity.User;

public interface UserService {
    // 登录验证
    void loginVerification(String username, String password);
}
