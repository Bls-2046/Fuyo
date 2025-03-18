package com.github.backend.controller;

import com.github.backend.dto.*;
import com.github.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // 登录验证
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = new LoginResponse();

        try {
            String username = request.getUsername();
            String password = request.getPassword();

            userService.loginVerification(username, password);

            response.setStatus(200);
            response.setMessage("登录成功");
            response.setId(username);

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // 获取用户基本信息
    @PostMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestBody UserInfoRequest request) {

        UserInfoResponse response = new UserInfoResponse();

        try {
            String username = request.getUsername();

            response.setStatus(200);
            response.setMessage("find is success");
            response.setData(userService.getUserInfo(username));
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    // 获取课表信息
    @PostMapping("/tabletime")
    public ResponseEntity<TabletimeResponse> getTabletime(@RequestBody TabletimeRequest request) {
        return null;
    }
}
