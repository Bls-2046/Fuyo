package com.github.backend.service;

import com.github.dto.schedule.AddScheduleRequest;
import com.github.dto.schedule.DeleteScheduleRequest;
import com.github.dto.schedule.MarkRemindedScheduleForClientRequest;
import com.github.dto.wechat.UpdateWeChatNicknameRequest;

/**
 * 数据更新方法接口
 */
public interface UpdateDataService {
// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Schedule /////////////////////////////////////////////

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

    // WeChat

    /**
     * 更新用户微信名称
     * @param updateWeChatNicknameRequest
     * @return Boolean
     */
    Boolean updateWeChatNickname(UpdateWeChatNicknameRequest updateWeChatNicknameRequest);
}
