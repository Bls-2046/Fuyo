package com.github.backend.service.impl;

import com.github.backend.service.DeepseekService;
import com.github.dto.deepseek.DeepseekChatRequest;
import com.github.dto.user.LoginRequest;
import com.github.backend.service.OperationService;
import com.github.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationServiceImpl implements OperationService {
    private final UserService userService;
    private final DeepseekService deepseekService;

    public OperationServiceImpl(UserService userService, DeepseekService deepseekService) {
        this.userService = userService;
        this.deepseekService = deepseekService;
    }

    @Override
    @Transactional
    public String loginVerification(LoginRequest loginRequest) {
        return userService.loginVerification(loginRequest);
    }

    @Override
    public String deepseekChat(DeepseekChatRequest deepseekChatRequest) {
        return deepseekService.deepseekChatRespo(deepseekChatRequest);
    }
}
