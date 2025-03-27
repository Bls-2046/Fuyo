package com.github.backend.dto.schedule;

import lombok.Data;

@Data
public class FetchScheduleRequest {
    private String username;
    private String openid;
}
