package com.github.backend.controller;

import com.github.backend.dto.user.LoginRequest;
import com.github.backend.dto.user.LoginResponse;
import com.github.backend.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/operation")
@Validated
public class OperationController {
    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    /**
     * 用户身份验证
     * @param loginRequest 验证请求体
     * @return LoginResponse
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = new LoginResponse();

        try {
            boolean isAuthenticated = operationService.loginVerification(loginRequest);

            if (isAuthenticated) {
                // 2. 认证成功
                loginResponse.setStatus(HttpStatus.OK.value())
                        .setMessage("登录成功");
                return ResponseEntity.ok(loginResponse);
            } else {
                // 3. 认证失败
                loginResponse.setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setMessage("用户名或密码错误");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
            }
        } catch (Exception e) {
            // 5. 系统异常处理
            log.error("登录异常: username={}", loginRequest.getUsername(), e);
            loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("系统繁忙，请稍后重试");
            return ResponseEntity.internalServerError().body(loginResponse);
        }
    }
}
