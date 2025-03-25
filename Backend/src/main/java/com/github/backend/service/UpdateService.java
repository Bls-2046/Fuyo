package com.github.backend.service;

import com.github.backend.dto.user.ScheduleRequest;

public interface UpdateService {
    Boolean uploadNickname(String username, String nickname);

    Boolean uploadSchedule(ScheduleRequest schedule);
}
