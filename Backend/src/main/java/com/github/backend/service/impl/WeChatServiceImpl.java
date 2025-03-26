package com.github.backend.service.impl;

import com.github.backend.dto.user.NicknameRequest;
import com.github.backend.entity.UserEntity;
import com.github.backend.repository.UserRepository;
import com.github.backend.repository.WeChatUserRepository;
import com.github.backend.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 编写微信的各种操作逻辑
 */
@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {
    private final UserRepository userRepository;
    private final WeChatUserRepository weChatUserRepository;

    @Autowired
    public WeChatServiceImpl(UserRepository userRepository, WeChatUserRepository weChatUserRepository) {
        this.userRepository = userRepository;
        this.weChatUserRepository = weChatUserRepository;
    }

    @Override
    public Boolean updateWeChatNickname(NicknameRequest nicknameRequest) {
        String username = nicknameRequest.getUsername();
        String nickname = nicknameRequest.getNickname();

        String queryNickname = weChatUserRepository.findNicknameByNickname(nickname);

        if (Objects.equals(queryNickname, nickname)) {
            UserEntity user = userRepository.findByUsername(username);
            if (Objects.nonNull(user)) {
                user.setNickname(nickname);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
