package com.github.backend.controller;

import com.github.backend.dto.schedule.*;
import com.github.backend.dto.wechat.UpdateWeChatNicknameRequest;
import com.github.backend.dto.wechat.UpdateWeChatNicknameResponse;
import com.github.backend.service.UpdateDataService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO 修改全部方法的返回值

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
     * @param updateWeChatNicknameRequest 请求体
     * @return ResponseEntity<UpdateWeChatNicknameResponse>
     */
    @PutMapping("/nickname")
    public ResponseEntity<UpdateWeChatNicknameResponse> upload(
            @RequestBody UpdateWeChatNicknameRequest updateWeChatNicknameRequest) {

        UpdateWeChatNicknameResponse response = new UpdateWeChatNicknameResponse();
        String username = updateWeChatNicknameRequest.getUsername();
        String nickname = updateWeChatNicknameRequest.getNickname();

        try {
            // 参数校验
            if (StringUtils.isBlank(username) || StringUtils.isBlank(nickname)) {
                response.setStatus(HttpStatus.BAD_REQUEST.value())
                        .setMessage("用户名或昵称不能为空");

                log.error("参数校验失败: {}", response);
                return ResponseEntity.badRequest().body(response);
            }

            // 业务处理
            Boolean uploadResult = updateDataService.updateWeChatNickname(updateWeChatNicknameRequest);

            if (uploadResult) {
                response.setStatus(HttpStatus.OK.value())
                        .setMessage("微信昵称上传成功");

                log.info("昵称上传成功: username={}", username);
                return ResponseEntity.ok(response);
            } else {
                // 业务预期内的失败
                response.setStatus(HttpStatus.BAD_REQUEST.value())
                        .setMessage("微信昵称上传失败（可能原因: 用户未关注微信公众号）");

                log.warn("昵称上传失败: username={}", username);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (RuntimeException e) {
            // 业务异常
            response.setStatus(HttpStatus.CONFLICT.value())
                    .setMessage(e.getMessage());

            log.error("昵称上传业务异常: username={}", username, e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            // 系统异常
            log.error("昵称上传系统异常: username={}", username, e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("系统繁忙，请稍后重试");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 用户上传新的日程信息
     * @param addScheduleRequest 用户新增日程安排信息
     * @return ResponseEntity<AddScheduleResponse>
     */
    @PostMapping("/schedule/add")
    public ResponseEntity<AddScheduleResponse> addSchedule(
            @RequestBody AddScheduleRequest addScheduleRequest) {

        AddScheduleResponse response = new AddScheduleResponse();
        String username = addScheduleRequest.getUsername();

        try {
            // 1. 参数校验
            if (StringUtils.isBlank(username) || addScheduleRequest.getSchedule() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value())
                        .setMessage("用户名和日程信息不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            // 2. 业务处理 & 类型转换
            AddScheduleRequest.RequestSchedule requestSchedule = addScheduleRequest.getSchedule();
            Boolean addResult = updateDataService.addSchedule(addScheduleRequest);

            if (addResult) {
                // 构建响应体中的Schedule对象
                AddScheduleResponse.ResponseSchedule responseSchedule =
                        new AddScheduleResponse.ResponseSchedule()
                                .setTitle(requestSchedule.getTitle())
                                .setDateTime(requestSchedule.getDateTime())
                                .setReminderDateTime(requestSchedule.getReminderDateTime())
                                .setDescription(requestSchedule.getDescription());

                response.setStatus(HttpStatus.OK.value())
                        .setMessage("日程添加成功")
                        .setSchedule(responseSchedule);

                log.info("日程添加成功: username={}", username);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value())
                        .setMessage("日程添加失败");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.CONFLICT.value())
                    .setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("系统繁忙，请稍后重试");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 删除用户指定的日程信息
     * @param deleteScheduleRequest 指定的日程信息
     * @return DeleteScheduleResponse
     */
    // TODO 修改方法 updateDataService.deleteSchedule(deleteScheduleRequest) 的返回值
    @DeleteMapping("/schedule/delete")
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
    @PutMapping("/schedule/mark-reminder-for-client")
    public MarkRemindedScheduleForClientResponse markRemindedScheduleForClient(
            @RequestBody MarkRemindedScheduleForClientRequest markRemindedScheduleForClientRequest) {

        MarkRemindedScheduleForClientResponse markRemindedScheduleForClientResponse = new MarkRemindedScheduleForClientResponse();

        try {
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

    /**
     * 更新用户微信昵称
     * @param updateWeChatNicknameRequest 更新微信用户名称请求体
     * @return UpdateNicknameResponse
     */
    @PutMapping("/wechat/nickname")
    public UpdateWeChatNicknameResponse updateNickname(@RequestBody UpdateWeChatNicknameRequest updateWeChatNicknameRequest) {
        UpdateWeChatNicknameResponse updateWeChatNicknameResponse = new UpdateWeChatNicknameResponse();
        try {
            Boolean updateResult = updateDataService.updateWeChatNickname(updateWeChatNicknameRequest);

            if (updateResult) {
                updateWeChatNicknameResponse.setStatus(200);
            } else {
                updateWeChatNicknameResponse.setStatus(400);
            }
        } catch (Exception e){
            updateWeChatNicknameResponse.setStatus(500);
        }
        return updateWeChatNicknameResponse;
    }
}
