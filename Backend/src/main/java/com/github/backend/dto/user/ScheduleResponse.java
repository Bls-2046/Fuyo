package com.github.backend.dto.user;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduleResponse {
    private int status;
    private String message;
    private List<Schedule> schedule;

    @Data
    public static class Schedule {
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDatetime;
        private String description;
    }
}
