package com.github.fuyo.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {
    private String title;
    private LocalDateTime dataTime;
    private LocalDateTime reminderDateTime;
    private String description;
}
