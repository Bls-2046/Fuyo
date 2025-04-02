package com.github.backend.service.impl.Impls;

import com.github.dto.wechat.UpdateWeChatNicknameRequest;
import com.github.backend.entity.mysql.UserEntity;
import com.github.backend.entity.mysql.WeChatUserEntity;
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

    /**
     *
     * @param updateWeChatNicknameRequest 微信昵称
     * @return Boolean
     */
    @Override
    public Boolean updateWeChatNickname(UpdateWeChatNicknameRequest updateWeChatNicknameRequest) {
        try {
            String username = updateWeChatNicknameRequest.getUsername();
            String nickname = updateWeChatNicknameRequest.getNickname();

            WeChatUserEntity queryResult = weChatUserRepository.findNicknameByNickname(nickname);
            String newNickname = queryResult.getNickname();

            if (newNickname != null) {
                if (Objects.equals(newNickname, nickname)) {
                    UserEntity user = userRepository.findByUsername(username);
                    if (Objects.nonNull(user)) {

                        // 保存微信昵称到用户基本信息表
                        user.setNickname(nickname);
                        userRepository.save(user);

                        return true;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
