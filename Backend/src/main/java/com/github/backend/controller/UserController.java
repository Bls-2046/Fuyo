package com.github.backend.controller;

import com.github.backend.dto.LoginRequest;
import com.github.backend.dto.LoginResponse;
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
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = new LoginResponse();

        try {
            Map<String, Object> data = request.getData();
            String username = (String) data.get("username");
            String password = (String) data.get("password");

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
}
