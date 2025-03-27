package com.github.backend.dto.schedule;

import lombok.Data;

/**
 * 添加用户日程的响应体
 */
@Data
public class AddScheduleResponse {
    private int status;
    private String message;
    private Schedule schedule;
}
