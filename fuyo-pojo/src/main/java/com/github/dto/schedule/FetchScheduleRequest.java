package com.github.dto.schedule;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FetchScheduleRequest {
    private String username;
}
