package com.github.backend.controller;

import com.github.backend.dto.user.NicknameRequest;
import com.github.backend.dto.user.NicknameResponse;
import com.github.backend.dto.user.ScheduleRequest;
import com.github.backend.dto.user.ScheduleResponse;
import com.github.backend.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 前端上传和保存信息
 */
@RestController
@RequestMapping("/api/update")
public class UpdateController {
    private final UpdateService updateService;

    @Autowired
    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    /**
     * 上传微信用户名(需要关注微信公众号)
     * @param nicknameRequest 请求体
     * @return NicknameResponse
     */
    @PostMapping("/nickname")
    public NicknameResponse upload(@RequestBody NicknameRequest nicknameRequest) {
        NicknameResponse nicknameResponse = new NicknameResponse();

        String username = nicknameRequest.getUsername();
        String nickname = nicknameRequest.getNickname();

        try {
            Boolean uploadResult = updateService.uploadNickname(username, nickname);

            if (uploadResult) {
                nicknameResponse.setStatus(200);
                nicknameResponse.setMessage("Successfully uploaded nickname");
            } else {
                nicknameResponse.setStatus(400);
                nicknameResponse.setMessage("Failed to upload nickname");
            }
        } catch (Exception e) {
            nicknameResponse.setStatus(500);
            nicknameResponse.setMessage(e.getMessage());
        }

        return nicknameResponse;
    }

    /**
     * 用户上传新的日程信息
     * @param scheduleRequest 用户新增日程安排信息
     * @return ScheduleResponse
     */
    @PostMapping("/schedule/add")
    public ScheduleResponse uploadSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        ScheduleResponse scheduleResponse = new ScheduleResponse();

        try {
            Boolean uploadResult = updateService.uploadSchedule(scheduleRequest);

            if (uploadResult) {
                scheduleResponse.setStatus(200);
                scheduleResponse.setMessage("Successfully uploaded schedule");
            } else {
                scheduleResponse.setStatus(400);
                scheduleResponse.setMessage("Failed to upload schedule");
            }
        } catch (Exception e) {
            scheduleResponse.setStatus(500);
            scheduleResponse.setMessage(e.getMessage());
        }

        return scheduleResponse;
    }
}
