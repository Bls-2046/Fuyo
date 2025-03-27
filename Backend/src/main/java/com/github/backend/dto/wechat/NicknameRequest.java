package com.github.backend.dto.wechat;

import lombok.Data;

@Data
public class NicknameRequest {
    private String username;
    private String nickname;
}
