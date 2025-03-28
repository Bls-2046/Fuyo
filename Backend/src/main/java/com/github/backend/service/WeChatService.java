package com.github.backend.service;

import com.github.backend.dto.wechat.UpdateWeChatNicknameRequest;

/**
 * 微信相关操作接口
 */
public interface WeChatService {
    /**
     * 更新用户微信昵称
     * @param updateWeChatNicknameRequest 微信昵称
     * @return Boolean
     */
    Boolean updateWeChatNickname(UpdateWeChatNicknameRequest updateWeChatNicknameRequest);
}
