package com.github.fuyo.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FetchScheduleResponse {
    private int status;
    private String message;
    private List<Schedule> schedule;

    @Data
    public static class Schedule {
        private String id;
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
        private Boolean isReminderInClient;
    }
}
