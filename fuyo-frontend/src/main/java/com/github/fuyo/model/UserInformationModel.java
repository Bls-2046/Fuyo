package com.github.fuyo.model;

import com.github.dto.user.FetchUserBaseInformationRequest;
import com.github.dto.user.FetchUserBaseInformationResponse;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class UserInformationModel {
    public UserInformationModel() {}

    public static void getUserInformation(String username) {
        String url = "http://localhost:8080/api/fetch/user-base-information";

        FetchUserBaseInformationRequest fetchUserBaseInformationRequest = new FetchUserBaseInformationRequest();
        fetchUserBaseInformationRequest.setUsername(username);

        try {
            FetchUserBaseInformationResponse userInformationResponse = Https.post(
                    url,
                    fetchUserBaseInformationRequest,
                    null,
                    FetchUserBaseInformationResponse.class
            );

            // 使用同步块确保线程安全
            synchronized (UserEntity.getUserInformation()) {
                UserEntity.getUserInformation().setUsername(userInformationResponse.getData().getUsername());
                UserEntity.getUserInformation().setName(userInformationResponse.getData().getName());
                UserEntity.getUserInformation().setDepartment(userInformationResponse.getData().getDepartment());
                UserEntity.getUserInformation().setEmail(userInformationResponse.getData().getEmail());
                UserEntity.getUserInformation().setPhone(userInformationResponse.getData().getPhone());
                // UserEntity.getUserInformation().setCookie(userInformationResponse.getData());
                UserEntity.getUserInformation().getWechatUser().setNickname(userInformationResponse.getData().getNickname());

                log.info("成功保存用户基本数据: {}", userInformationResponse);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
