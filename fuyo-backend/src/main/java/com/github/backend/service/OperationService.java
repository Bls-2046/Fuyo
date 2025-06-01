package com.github.backend.service;

import com.github.dto.deepseek.DeepseekChatRequest;
import com.github.dto.user.LoginRequest;

public interface OperationService {
    /**
     * 用户登录验证
     * 通过 python 脚本 login_bitzh.py 模拟账号登录
     * @param loginRequest 登录请求体
     */
    String loginVerification(LoginRequest loginRequest);

    String deepseekChat(DeepseekChatRequest deepseekChatRequest);
}
