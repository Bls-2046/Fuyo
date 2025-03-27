package com.github.backend.service;

import com.github.backend.dto.schedule.AddScheduleRequest;
import com.github.backend.dto.schedule.DeleteScheduleRequest;
import com.github.backend.dto.schedule.MarkRemindedScheduleForClientRequest;
import com.github.backend.dto.wechat.NicknameRequest;

/**
 * 数据更新方法
 */
public interface UpdateService {
    /**
     * 更新微信昵称
     * @param nicknameRequest 用户名与微信昵称
     * @return Boolean
     */
    Boolean updateWeChatNickname(NicknameRequest nicknameRequest);

    /**
     * 添加日程信息
     * @param schedule 日程信息
     * @return Boolean
     */
    Boolean addSchedule(AddScheduleRequest schedule);

    /**
     * 删除指定日程信息
     * @param schedule 日程信息
     * @return Boolean
     */
    Boolean deleteSchedule(DeleteScheduleRequest schedule);

    /**
     * 为前端已发送日程提醒弹窗做标记
     */
    Boolean markRemindedScheduleForClient(
            MarkRemindedScheduleForClientRequest markRemindedScheduleForClientRequest
    );
}
