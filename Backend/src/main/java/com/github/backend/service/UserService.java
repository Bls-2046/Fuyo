package com.github.backend.service;

import com.github.backend.dto.user.LoginRequest;
import com.github.backend.dto.user.FetchUserBaseInformationRequest;
import com.github.backend.dto.user.FetchUserBaseInformationResponse;

/**
 * 用户各类操作接口
 */
public interface UserService {
    /**
     * 用户登录验证
     * @param loginRequest 登录信息
     * @return Boolean
     */
    Boolean loginVerification(LoginRequest loginRequest);

    /**
     * 获得用户信息
     * @param fetchUserBaseInformationRequest 用户名
     * @return UserInformationResponse.UserInformation
     */
    FetchUserBaseInformationResponse.UserInformation fetchUserBaseInformation(FetchUserBaseInformationRequest fetchUserBaseInformationRequest);
}
