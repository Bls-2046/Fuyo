package com.github.backend.controller;

import com.github.backend.dto.schedule.*;
import com.github.backend.dto.wechat.NicknameRequest;
import com.github.backend.dto.wechat.NicknameResponse;
import com.github.backend.service.UpdateDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 前端上传和保存信息
 */
@Slf4j
@RestController
@RequestMapping("/api/update")
public class UpdateDataController {
    private final UpdateDataService updateDataService;

    @Autowired
    public UpdateDataController(UpdateDataService updateDataService) {
        this.updateDataService = updateDataService;
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Schedule /////////////////////////////////////////////
    /**
     * 上传微信用户名(需要关注微信公众号)
     * @param nicknameRequest 请求体
     * @return NicknameResponse
     */
    @PostMapping("/nickname")
    public NicknameResponse upload(@RequestBody NicknameRequest nicknameRequest) {
        NicknameResponse nicknameResponse = new NicknameResponse();

        try {
            Boolean uploadResult = updateDataService.updateWeChatNickname(nicknameRequest);

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
     * @param addScheduleRequest 用户新增日程安排信息
     * @return ScheduleResponse
     */
    @PostMapping("/schedule/add")
    public AddScheduleResponse addSchedule(@RequestBody AddScheduleRequest addScheduleRequest) {
        AddScheduleResponse addScheduleResponse = new AddScheduleResponse();
        System.out.println(addScheduleRequest);
        try {
            Boolean addResult = updateDataService.addSchedule(addScheduleRequest);

            if (addResult) {
                addScheduleResponse.setStatus(200);
                addScheduleResponse.setMessage("Successfully uploaded schedule");
            } else {
                addScheduleResponse.setStatus(400);
                addScheduleResponse.setMessage("Failed to upload schedule");
            }
        } catch (Exception e) {
            addScheduleResponse.setStatus(500);
            addScheduleResponse.setMessage(e.getMessage());
        }

        return addScheduleResponse;
    }

    /**
     * 删除用户指定的日程信息
     * @param deleteScheduleRequest 指定的日程信息
     * @return DeleteScheduleResponse
     */
    @PostMapping("/schedule/delete")
    public DeleteScheduleResponse deleteSchedule(@RequestBody DeleteScheduleRequest deleteScheduleRequest) {
        DeleteScheduleResponse deleteScheduleResponse = new DeleteScheduleResponse();

        try {
            Boolean deleteResult = updateDataService.deleteSchedule(deleteScheduleRequest);

            if (deleteResult) {
                deleteScheduleResponse.setStatus(200);
                deleteScheduleResponse.setMessage("Successfully deleted");
            } else {
                deleteScheduleResponse.setStatus(400);
                deleteScheduleResponse.setMessage("Failed to deleted");
            }
        } catch (Exception e) {
            deleteScheduleResponse.setStatus(500);
            deleteScheduleResponse.setMessage(e.getMessage());
        }
        return deleteScheduleResponse;
    }

    /**
     * 对已显示提示框日程作标记
     * @param markRemindedScheduleForClientRequest 已显示提示框的日程信息
     * @return MarkRemindedScheduleForClientResponse
     */
    @PostMapping("/schedule/mark-reminder-for-client")
    public MarkRemindedScheduleForClientResponse markRemindedScheduleForClient(
            @RequestBody MarkRemindedScheduleForClientRequest markRemindedScheduleForClientRequest) {

        MarkRemindedScheduleForClientResponse markRemindedScheduleForClientResponse = new MarkRemindedScheduleForClientResponse();

        try {
            log.info(String.valueOf(markRemindedScheduleForClientRequest));
            Boolean markResult = updateDataService.markRemindedScheduleForClient(markRemindedScheduleForClientRequest);

            if (markResult) {
                markRemindedScheduleForClientResponse.setStatus(200);
            } else {
                markRemindedScheduleForClientResponse.setStatus(400);
            }
        } catch (Exception e) {
            markRemindedScheduleForClientResponse.setStatus(500);
        }
        return markRemindedScheduleForClientResponse;
    }
}
