package com.github.backend.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 添加用户日程的请求体
 */
@Data
public class AddScheduleRequest {
    private String username;
    private String openid;
    private Schedule schedule;

    @Data
    public static class Schedule {
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
    }
}
