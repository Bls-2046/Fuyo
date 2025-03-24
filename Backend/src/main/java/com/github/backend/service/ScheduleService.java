package com.github.backend.service;

import com.github.backend.dto.user.ScheduleRequest;

public interface ScheduleService {
    Boolean addScheduleInfo(ScheduleRequest schedule);
}
