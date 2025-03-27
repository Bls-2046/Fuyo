package com.github.backend.dto.schedule;

import lombok.Data;

/**
 * 标记前端已显示过提醒弹窗用户日程的请求体
 */
@Data
public class MarkRemindedScheduleForClientRequest {
    private String username;
    private String openid;
    private Schedule schedule;
}
