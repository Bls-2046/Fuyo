package com.github.backend.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleRequest {
    private String username;
    private String openid;
    private Schedule schedule;
}
