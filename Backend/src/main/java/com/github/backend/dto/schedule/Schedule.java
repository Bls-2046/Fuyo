package com.github.backend.dto.schedule;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {
    private String id;
    private String title;
    private LocalDateTime dateTime;
    private LocalDateTime reminderDateTime;
    private String description;
}
