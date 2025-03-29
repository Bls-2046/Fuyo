package com.github.backend.service.impl;

import com.github.backend.dto.user.LoginRequest;
import com.github.backend.service.OperationService;
import com.github.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationServiceImpl implements OperationService {
    private final UserService userService;

    public OperationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public String loginVerification(LoginRequest loginRequest) {
        return userService.loginVerification(loginRequest);
    }
}
