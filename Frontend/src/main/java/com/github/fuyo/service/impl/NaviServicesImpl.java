package com.github.fuyo.service.impl;

import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.service.NaviServices;

import java.util.ArrayList;
import java.util.List;

public class NaviServicesImpl implements NaviServices {

    // TODO: 开发阶段，返回固定数据，请修改此处以获取完整数据。请实现Service

    @Override
    public UserEntity getUserInformation(Integer userId) {

        UserEntity userEntity = new UserEntity();
        userEntity.setId("X");
        userEntity.setUsername("X");
        userEntity.setName("X");
        userEntity.setEmail("X@X.X.X");
        userEntity.setPhone("X");

        List<UserEntity.Tabletime> classTable = new ArrayList<>();

        classTable.add(new UserEntity.Tabletime(null,null,1,1,1, 1, "单周", "eee", 1, 1));
        userEntity.setTabletime(classTable);

        return userEntity;
    }
}
