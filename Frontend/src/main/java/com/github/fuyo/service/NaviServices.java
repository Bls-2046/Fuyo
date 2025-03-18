package com.github.fuyo.service;

import com.github.fuyo.entity.UserEntity;

public interface NaviServices {

    /**
     * 获取用户信息
     * @return 用户所有信息(包含课表)
     */
    UserEntity getUserInformation(Integer userId);



}
