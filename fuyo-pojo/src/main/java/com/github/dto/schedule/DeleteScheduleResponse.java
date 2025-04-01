package com.github.dto.schedule;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class DeleteScheduleResponse {
    private int status;
    private String message;
    private Schedule schedule;

    @Data
    @Accessors(chain = true)
    public static class Schedule {
        private String id;
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
    }
}