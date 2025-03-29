package com.github.fuyo.model;

import com.github.fuyo.dto.UserInformationRequest;
import com.github.fuyo.dto.UserInformationResponse;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class UserInformationModel {
    public UserInformationModel() {}

    public static void getUserInformation(String username) {
        String url = "http://localhost:8080/api/fetch/user-base-information";

        UserInformationRequest userInformationRequest = new UserInformationRequest();
        userInformationRequest.setUsername(username);

        try {
            UserInformationResponse userInformationResponse = Https.post(url, userInformationRequest, null, UserInformationResponse.class);

            // 使用同步块确保线程安全
            synchronized (UserEntity.getUserInformation()) {
                UserEntity.getUserInformation().setUsername(userInformationResponse.getData().get("username"));
                UserEntity.getUserInformation().setName(userInformationResponse.getData().get("name"));
                UserEntity.getUserInformation().setDepartment(userInformationResponse.getData().get("department"));
                UserEntity.getUserInformation().setEmail(userInformationResponse.getData().get("email"));
                UserEntity.getUserInformation().setPhone(userInformationResponse.getData().get("phone"));
                UserEntity.getUserInformation().setCookie(userInformationResponse.getData().get("cookie"));
                UserEntity.getUserInformation().getWechatUser().setNickname(userInformationResponse.getData().get("nickname"));

                log.info("成功保存用户基本数据: {}", userInformationResponse);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
