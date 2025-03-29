package com.github.backend.dto.schedule;

import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class FetchScheduleResponse {
    private int status;
    private String message;
    private List<Schedule> schedule;

    @Data
    @Accessors(chain = true)
    public static class Schedule {
        private String id;
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
        private Boolean isReminderInClient;
    }
}