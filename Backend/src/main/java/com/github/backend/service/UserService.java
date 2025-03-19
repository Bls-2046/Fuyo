package com.github.backend.service;

import com.github.backend.dto.TabletimeResponse;
import com.github.backend.dto.UserInformationResponse;

public interface UserService {
    // 登录验证
    void loginVerification(String username, String password);
    // 获得学生信息
    UserInformationResponse.UserInformation getUserInformation(String username);
    // 获得学生课表
    TabletimeResponse.Tabletime getTabletime(String username);
}
