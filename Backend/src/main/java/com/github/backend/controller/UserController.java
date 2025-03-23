/**
 * 包含用户获取信息的url, 仅支持 Post 请求
 * localhost:8080/api/user/login 登录验证
 * localhost:8080/api/user/information 获取个人基本信息
 * localhost:8080/api/user/tabletime 获取课表信息
 */

package com.github.backend.controller;

import com.github.backend.dto.user.*;
import com.github.backend.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取用户的各种信息
 */
@RestController
@RequestMapping("/api/user")
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
                loginResponse.setMessage("login success");
                loginResponse.setId(username);
            } else {
                loginResponse.setStatus(204);
            }
        } catch (Exception e) {
            loginResponse.setStatus(500);
            loginResponse.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * 获取用户基本信息
     *
     */
    @PostMapping("/information")
    public UserInformationResponse getUserInformation(@RequestBody UserInformationRequest userInformationRequest) {

        UserInformationResponse userInformationResponse = new UserInformationResponse();

        try {
            // 获取前端用户输入数据
            String username = userInformationRequest.getUsername();

            // 获取成功放回响应
            userInformationResponse.setData(userService.getUserInformation(username));
            if (userInformationResponse.getData() != null) {
                userInformationResponse.setStatus(200);
                userInformationResponse.setMessage("get information successful");
            } else {
                userInformationResponse.setStatus(204);
                userInformationResponse.setMessage("get information failed");
            }
        } catch (Exception e) {
            userInformationResponse.setStatus(500);
            userInformationResponse.setMessage(e.getMessage());
        }
        return userInformationResponse;
    }

    /**
     * 获取课表信息
     * @param tabletimeRequest 请求
     * @return TabletimeResponse
     */
    @PostMapping("/tabletime")
    public TabletimeResponse getTabletime(@RequestBody TabletimeRequest tabletimeRequest) {

        TabletimeResponse tabletimeResponse = new TabletimeResponse();

        try {
            String username = tabletimeRequest.getUsername();

            tabletimeResponse.setTabletime(userService.getTabletime(username));

            tabletimeResponse.setStatus(200);
            tabletimeResponse.setMessage("get tabletime successful");

        } catch (Exception e) {
            tabletimeResponse.setStatus(500);
            tabletimeResponse.setMessage(e.getMessage());
        }
        return tabletimeResponse;
    }


    /**
     * 获取用户的日程安排信息
     * @param scheduleRequest
     * @return
     */
    @PostMapping("/schedule")
    public ScheduleResponse getSchedule(@RequestBody ScheduleRequest scheduleRequest) {

        ScheduleResponse scheduleResponse = new ScheduleResponse();

        try {
            String username = scheduleRequest.getUsername();

            scheduleResponse.setSchedule(userService.getSchedule(username));

            scheduleResponse.setStatus(200);
            scheduleResponse.setMessage("get schedule successful");

        } catch(Exception e) {
            scheduleResponse.setStatus(500);
            scheduleResponse.setMessage(e.getMessage());
        }
        return scheduleResponse;
    }
}
