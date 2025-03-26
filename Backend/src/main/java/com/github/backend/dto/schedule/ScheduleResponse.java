package com.github.backend.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduleResponse {
    private int status;
    private String message;
    private List<Schedule> schedule;
}
