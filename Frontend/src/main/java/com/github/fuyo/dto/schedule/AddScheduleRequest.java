package com.github.fuyo.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AddScheduleRequest {
    private String username;
    private String nickname;
    private Schedule schedule;

    @Data
    public static class Schedule {
        private String title;
        private LocalDateTime dateTime;
        private LocalDateTime reminderDateTime;
        private String description;
    }
}
