package com.github.fuyo.dto.schedule;

import lombok.Data;

@Data
public class DeleteScheduleRequest {
    private String username;
    private String openid;
    private Schedule schedule;
}
