package com.github.backend.service;

import com.github.backend.dto.schedule.AddScheduleRequest;
import com.github.backend.dto.schedule.DeleteScheduleRequest;

public interface ScheduleService {
    Boolean addScheduleInfo(AddScheduleRequest schedule);
    Boolean deleteScheduleInfo(DeleteScheduleRequest schedule);
}
