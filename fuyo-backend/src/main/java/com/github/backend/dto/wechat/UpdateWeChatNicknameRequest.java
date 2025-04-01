package com.github.backend.dto.wechat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateWeChatNicknameRequest {
    private String username;
    private String nickname;
}