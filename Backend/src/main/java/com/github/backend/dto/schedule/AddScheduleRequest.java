package com.github.backend.dto.schedule;

import lombok.Data;

/**
 * 添加用户日程的请求体
 */
@Data
public class AddScheduleRequest {
    private String username;
    private String openid;
    private Schedule schedule;
}
