package com.github.backend.dto.schedule;

import lombok.Data;

@Data
public class DeleteScheduleResponse {
    private int status;
    private String message;
    private Schedule schedule;
}
