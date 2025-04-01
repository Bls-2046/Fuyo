package com.github.dto.schedule;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeleteScheduleRequest {
    private String id;
    private String username;
}
