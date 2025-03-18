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

        classTable.add(new UserEntity.Tabletime(null,null,1,2,"计算机网络<br/>1-16周<br/>MB206【03-04】"));
        classTable.add(new UserEntity.Tabletime(null,null,1,6,"机械奥妙之旅<br/>1-16周<br/>MB204【11-12】"));
        classTable.add(new UserEntity.Tabletime(null,null,1,5,"排球<br/>1-16周<br/>文体综合馆【08-09】"));
        classTable.add(new UserEntity.Tabletime(null,null,1,1,"移动开发技术<br/>1-16周（单周）<br/>MB405【01-02】"));

        userEntity.setTabletimelist(classTable);
        return userEntity;
    }
}
