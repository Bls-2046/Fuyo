package com.github.backend.controller;

import com.github.backend.dto.*;
import com.github.backend.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // 登录验证
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = new LoginResponse();

        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            // 用户名密码验证
            if (userService.loginVerification(username, password)) {
                loginResponse.setStatus(200);
                loginResponse.setMessage("登录成功");
                loginResponse.setId(username);
            }
        } catch (Exception e) {
            loginResponse.setStatus(500);
            loginResponse.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * 获取用户基本信息
     * URL: localhost:8080/api/user/information
     */
    @PostMapping("/information")
    public ResponseEntity<UserInformationResponse> getUserInformation(@RequestBody UserInformationRequest userInformationRequest) {

        UserInformationResponse userInformationResponse = new UserInformationResponse();

        try {
            // 获取前端用户输入数据
            String username = userInformationRequest.getUsername();

            // 获取成功放回响应
            userInformationResponse.setData(userService.getUserInformation(username));
            userInformationResponse.setStatus(200);
            userInformationResponse.setMessage("success");

        } catch (Exception e) {
            userInformationResponse.setStatus(500);
            userInformationResponse.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(userInformationResponse);
    }

    /**
     * 获取课表信息
     *
     * @param tabletimeRequest 请求
     * @return TabletimeResponse
     */
    @PostMapping("/tabletime")
    public TabletimeResponse getTabletime(@RequestBody TabletimeRequest tabletimeRequest) {

        TabletimeResponse tabletimeResponse = new TabletimeResponse();

        try {
            String username = tabletimeRequest.getUsername();

            tabletimeResponse.setTabletime(userService.getTabletime(username));

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return tabletimeResponse;
    }
}
