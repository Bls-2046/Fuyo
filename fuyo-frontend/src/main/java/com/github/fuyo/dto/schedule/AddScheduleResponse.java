package com.github.fuyo.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;

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
