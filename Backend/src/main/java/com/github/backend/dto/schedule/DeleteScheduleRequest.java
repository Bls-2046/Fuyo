package com.github.backend.dto.schedule;

import lombok.Data;

/**
 * 删除用户日程的请求体
 */
@Data
public class DeleteScheduleRequest {
    private String id;
    private String username;
}
