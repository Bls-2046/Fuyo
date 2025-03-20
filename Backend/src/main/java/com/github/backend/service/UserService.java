package com.github.backend.service;

import com.github.backend.dto.TabletimeResponse;
import com.github.backend.dto.UserInformationResponse;

import java.util.List;

public interface UserService {
    // 登录验证
    Boolean loginVerification(String username, String password);
    // 获得学生信息
    UserInformationResponse.UserInformation getUserInformation(String username);
    // 获得学生课表
    List<TabletimeResponse.Tabletime> getTabletime(String username);
}
