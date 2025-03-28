package com.github.backend.dto.wechat;

import lombok.Data;

@Data
public class UpdateWeChatNicknameRequest {
    private String username;
    private String nickname;
}
