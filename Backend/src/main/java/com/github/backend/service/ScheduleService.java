package com.github.backend.service;

import com.github.backend.dto.schedule.*;

import java.util.List;

/**
 * Schedule 日程提醒相关操作接口
 */
public interface ScheduleService {
    /**
     * 返回用户的日程安排信息
     * @param username 用户名
     * @return List<ScheduleResponse.Schedule>
     */
    List<FetchScheduleResponse.Schedule> fetchSchedule(FetchScheduleRequest fetchScheduleRequest);

    /**
     * 添加用户日程信息
     * @param addScheduleRequest 日程信息
     * @return Boolean
     */
    Boolean addScheduleInfo(AddScheduleRequest addScheduleRequest);

    /**
     * 删除用户指定日程信息
     * @param deleteScheduleRequest 日程信息
     * @return Boolean
     */
    Boolean deleteScheduleInfo(DeleteScheduleRequest deleteScheduleRequest);

    /**
     * 将前端已提醒的内容进行标记
     * @param markRemindedScheduleForClientRequest 日程信息
     * @return Boolean
     */
    Boolean markRemindedScheduleForClient(MarkRemindedScheduleForClientRequest markRemindedScheduleForClientRequest);
}
