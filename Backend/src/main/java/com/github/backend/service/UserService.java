package com.github.backend.service;

import com.github.backend.dto.UserInfoResponse;

public interface UserService {
    // 登录验证
    void loginVerification(String username, String password);

    UserInfoResponse.UserInfo getUserInfo(String username);
}
