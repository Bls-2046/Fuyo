package com.github.backend.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleRequest {
    private String username;
    private Schedule schedule;

    @Data
    public static class Schedule {
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDatetime;
        private String description;
    }
}
