package com.github.dto.wechat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateWeChatNicknameResponse {
    private int status;
    private String message;
}