package com.github.backend.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 删除用户日程的响应体
 */
@Data
public class DeleteScheduleResponse {
    private int status;
    private String message;
    private Schedule schedule;

    @Data
    public static class Schedule {
        private String id;
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
    }
}
