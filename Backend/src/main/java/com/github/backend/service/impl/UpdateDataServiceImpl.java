package com.github.backend.service.impl;

import com.github.backend.dto.schedule.AddScheduleRequest;
import com.github.backend.dto.schedule.DeleteScheduleRequest;
import com.github.backend.dto.schedule.MarkRemindedScheduleForClientRequest;
import com.github.backend.dto.wechat.NicknameRequest;
import com.github.backend.service.ScheduleService;
import com.github.backend.service.UpdateDataService;
import com.github.backend.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户上传信息的逻辑处理
 */
@Service
public class UpdateDataServiceImpl implements UpdateDataService {
    private final ScheduleService scheduleService;
    private final WeChatService weChatService;

    @Autowired
    public UpdateDataServiceImpl(ScheduleService scheduleService, WeChatService weChatService) {
        this.scheduleService = scheduleService;
        this.weChatService = weChatService;
    }

    /**
     * 根据用户提供的微信名 nickname 在 WeChatUser 表中查找对应的名字
     * 若找到就根据 username 将用户微信名存入 User 表中
     * @param nicknameRequest
     * @return Boolean
     */
    @Override
    public Boolean updateWeChatNickname(NicknameRequest nicknameRequest) {
        return weChatService.updateWeChatNickname(nicknameRequest);
    }

    /**
     * 添加用户日程信息到数据库
     * @param addScheduleRequest 用户的日程信息
     * @return Boolean
     */
    @Override
    public Boolean addSchedule(AddScheduleRequest addScheduleRequest) {
        return scheduleService.addScheduleInfo(addScheduleRequest);
    }

    /**
     * 删除用户日程信息
     * @param deleteScheduleRequest 用户指定删除的日程信息
     * @return Boolean
     */
    @Override
    public Boolean deleteSchedule(DeleteScheduleRequest deleteScheduleRequest) {
        return scheduleService.deleteScheduleInfo(deleteScheduleRequest);
    }


    @Override
    public Boolean markRemindedScheduleForClient(
            MarkRemindedScheduleForClientRequest markRemindedScheduleForClientRequest
    ) {
        return scheduleService.markRemindedScheduleForClient(markRemindedScheduleForClientRequest);
    }
}
