package com.github.backend.service;

import com.github.backend.dto.user.NicknameRequest;

public interface WeChatService {
    Boolean updateWeChatNickname(NicknameRequest nicknameRequest);
}
