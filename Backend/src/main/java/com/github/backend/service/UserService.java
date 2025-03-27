package com.github.backend.service;

import com.github.backend.dto.schedule.Schedule;
import com.github.backend.dto.schedule.ScheduleResponse;
import com.github.backend.dto.user.TabletimeResponse;
import com.github.backend.dto.user.UserInformationResponse;

import java.util.List;

/**
 * 各功能核心逻辑接口
 */

public interface UserService {
    // 登录验证
    Boolean loginVerification(String username, String password);
    // 获得学生信息
    UserInformationResponse.UserInformation getUserInformation(String username);
    // 获得学生课表信息
    List<TabletimeResponse.Tabletime> getTabletime(String username);
    // 获取日程安排信息
    List<ScheduleResponse.Schedule> getSchedule(String username);
}
