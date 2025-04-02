package com.github.backend.controller;

import com.github.dto.user.LoginRequest;
import com.github.dto.user.LoginResponse;
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

import java.util.Objects;

/**
 * 用户的各种操作（如：登录、认证等）
 */
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
            String loginResult = operationService.loginVerification(loginRequest);

            if (Objects.equals(loginResult, "登录成功")) {
                loginResponse.setStatus(HttpStatus.OK.value())
                        .setMessage(loginResult);

                log.info(String.valueOf(loginResponse));

                return ResponseEntity.ok(loginResponse);
            } else {
                // 业务预期内的失败（密码错误、用户不存在）
                loginResponse.setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setMessage(loginResult);

                log.error(String.valueOf(loginResponse));

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
            }
        } catch (RuntimeException e) {
            // 已知的业务异常（密码错误），按预期返回 401，无需记录 ERROR 日志
            loginResponse.setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setMessage(e.getMessage()); // 直接返回异常消息

            log.error(String.valueOf(loginResponse));

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        } catch (Exception e) {
            // 真正的系统异常（如数据库连接失败）
            log.error("登录系统异常: username={}", loginRequest.getUsername(), e);
            loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("系统繁忙，请稍后重试");

            log.error(String.valueOf(loginResponse));

            return ResponseEntity.internalServerError().body(loginResponse);
        }
    }
}