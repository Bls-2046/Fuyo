package com.github.fuyo.dto.schedule;

import lombok.Data;

@Data
public class AddScheduleResponse {
    private int status;
    private String message;
    private Schedule schedule;
}
