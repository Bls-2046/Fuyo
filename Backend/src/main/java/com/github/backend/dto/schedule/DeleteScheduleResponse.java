package com.github.backend.dto.schedule;

import lombok.Data;

/**
 * 删除用户日程的响应体
 */
@Data
public class DeleteScheduleResponse {
    private int status;
    private String message;
    private Schedule schedule;
}
