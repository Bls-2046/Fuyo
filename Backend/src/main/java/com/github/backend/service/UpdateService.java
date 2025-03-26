package com.github.backend.service;

import com.github.backend.dto.schedule.AddScheduleRequest;
import com.github.backend.dto.schedule.DeleteScheduleRequest;
import com.github.backend.dto.user.NicknameRequest;

public interface UpdateService {
    /**
     * 更新微信昵称
     * @param nicknameRequest 用户名与微信昵称
     * @return Boolean
     */
    Boolean updateWeChatNickname(NicknameRequest nicknameRequest);
    Boolean addSchedule(AddScheduleRequest schedule);
    Boolean deleteSchedule(DeleteScheduleRequest schedule);
}
