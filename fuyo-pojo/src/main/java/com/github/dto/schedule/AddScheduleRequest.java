package com.github.dto.schedule;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AddScheduleRequest {
    private String username;
    private String nickname;
    private RequestSchedule schedule;

    @Data
    @Accessors(chain = true)
    public static class RequestSchedule {
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
    }
}