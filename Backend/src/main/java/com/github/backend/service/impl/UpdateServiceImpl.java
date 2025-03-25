package com.github.backend.service.impl;

import com.github.backend.dto.user.ScheduleRequest;
import com.github.backend.entity.UserEntity;
import com.github.backend.repository.UserRepository;
import com.github.backend.repository.WeChatUserRepository;
import com.github.backend.service.ScheduleService;
import com.github.backend.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户上传信息的逻辑处理
 */
@Service
public class UpdateServiceImpl implements UpdateService {
    private final WeChatUserRepository weChatUserRepository;
    private final UserRepository userRepository;
    private final ScheduleService scheduleService;

    @Autowired
    public UpdateServiceImpl(WeChatUserRepository weChatUserRepository, UserRepository userRepository, ScheduleService scheduleService) {
        this.weChatUserRepository = weChatUserRepository;
        this.userRepository = userRepository;
        this.scheduleService = scheduleService;
    }

    /**
     * 根据用户提供的微信名 nickname 在 WeChatUser 表中查找对应的名字
     * 若找到就根据 username 将用户微信名存入 User 表中
     * @param username 用户名
     * @param nickname 用户提供的微信名
     * @return Boolean
     */
    @Override
    public Boolean uploadNickname(String username, String nickname) {
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

    /**
     * 保存用户新增的日程信息到数据库
     * @param scheduleRequest 用户的日程信息
     * @return Boolean
     */
    @Override
    public Boolean uploadSchedule(ScheduleRequest scheduleRequest) {
        return scheduleService.addScheduleInfo(scheduleRequest);
    }
}
