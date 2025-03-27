package com.github.backend.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 添加用户日程的响应体
 */
@Data
public class AddScheduleResponse {
    private int status;
    private String message;
    private Schedule schedule;

    @Data
    public static class Schedule {
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
    }
}
