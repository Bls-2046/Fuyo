package com.github.dto.schedule;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AddScheduleResponse {
    private int status;
    private String message;
    private ResponseSchedule schedule;

    @Data
    @Accessors(chain = true)
    public static class ResponseSchedule {
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
    }
}