package com.github.dto.schedule;

import lombok.Data;

/**
 * 标记前端已显示过提醒弹窗用户日程的请求体
 */
@Data
public class MarkRemindedScheduleForClientRequest {
    private String id;
    private String username;
}
