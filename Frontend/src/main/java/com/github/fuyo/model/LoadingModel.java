package com.github.fuyo.model;

import com.github.fuyo.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadingModel {
    public LoadingModel() {}

    /**
     * 检查数据完整性
     * @return checkUsername && checkUser && checkDepartment && checkEmail && checkPhone && checkCookie
     */
    public Boolean checkDataIntegrity() {

        Boolean checkUsername = UserEntity.getUserInformation().getUsername() != null;
        Boolean checkName = UserEntity.getUserInformation().getName() != null;
        Boolean checkDepartment = UserEntity.getUserInformation().getDepartment() != null;
        Boolean checkEmail = UserEntity.getUserInformation().getEmail() != null;
        Boolean checkPhone = UserEntity.getUserInformation().getPhone() != null;

        // 这里需要等待课程表全部获取完毕

        return checkUsername && checkName && checkDepartment && checkEmail && checkPhone;
    }
}
