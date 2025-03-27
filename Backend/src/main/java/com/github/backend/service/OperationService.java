package com.github.backend.service;

import com.github.backend.dto.user.LoginRequest;

public interface OperationService {
    /**
     * 用户登录验证
     * 通过 python 脚本 login_bitzh.py 模拟账号登录
     * @param username 用于登录的用户名
     * @param password 用于登录的密码
     */
    Boolean loginVerification(LoginRequest loginRequest);
}
