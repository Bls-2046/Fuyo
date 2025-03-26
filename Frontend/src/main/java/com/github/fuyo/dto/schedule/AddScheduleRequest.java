package com.github.fuyo.dto.schedule;

import lombok.Data;


@Data
public class AddScheduleRequest {
    private String username;
    private String openid;
    private Schedule schedule;
}
