package com.github.backend.service;

import com.github.dto.user.LoginRequest;
import com.github.dto.user.FetchUserBaseInformationRequest;
import com.github.dto.user.FetchUserBaseInformationResponse;

/**
 * 用户各类操作接口
 */
public interface UserService {
    /**
     * 用户登录验证
     * @param loginRequest 登录信息
     * @return Boolean
     */
    String loginVerification(LoginRequest loginRequest);

    /**
     * 获得用户信息
     * @param fetchUserBaseInformationRequest 用户名
     * @return UserInformationResponse.UserInformation
     */
    FetchUserBaseInformationResponse.UserInformation fetchUserBaseInformation(FetchUserBaseInformationRequest fetchUserBaseInformationRequest);
}
