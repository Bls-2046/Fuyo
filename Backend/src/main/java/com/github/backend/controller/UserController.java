package com.github.backend.controller;

import com.github.backend.dto.GenericRequest;
import com.github.backend.dto.GenericResponse;
import com.github.backend.entity.User;
import com.github.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody GenericRequest request) {

        GenericResponse response = new GenericResponse();

        try {
            Map<String, Object> data = request.getData();
            String username = (String) data.get("username");
            String password = (String) data.get("password");

            User user = userService.login(username, password);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", user);

            response.setStatus(200);
            response.setMessage("登录成功");
            response.setData(responseData);

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
