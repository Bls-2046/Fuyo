package com.github.backend.dto.user;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduleResponse {
    private int status;
    private String message;
    private List<Schedule> schedulelist;

    @Data
    public static class Schedule {
        private String username;
        private String eventTitle;
        private LocalDateTime eventDateTime;
        private int earlyTime;
        private String earlyTimeType;
        private String eventDescription;
    }
}
