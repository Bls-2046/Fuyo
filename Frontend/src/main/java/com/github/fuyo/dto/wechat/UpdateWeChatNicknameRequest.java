package com.github.fuyo.dto.wechat;

import lombok.Data;

@Data
public class UpdateWeChatNicknameRequest {
    private String username;
    private String nickname;
}
