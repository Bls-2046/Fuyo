package com.github.fuyo.model;

import com.github.fuyo.dto.wechat.UpdateWeChatNicknameRequest;
import com.github.fuyo.dto.wechat.UpdateWeChatNicknameResponse;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class WeChatModel {
    public WeChatModel() {}

    public static Boolean updateWeChatNickName(String nickname) {
        String url = "http://127.0.0.1:8080/api/update/wechat/nickname";
        try {
            UpdateWeChatNicknameRequest updateWeChatNicknameRequest = new UpdateWeChatNicknameRequest();
            updateWeChatNicknameRequest.setUsername(UserEntity.getUserInformation().getUsername());
            updateWeChatNicknameRequest.setNickname(nickname);

            UpdateWeChatNicknameResponse updateWeChatNicknameResponse =
                    Https.put(url, updateWeChatNicknameRequest, null, UpdateWeChatNicknameResponse.class);

            if (updateWeChatNicknameResponse.getStatus() == 200) {
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
