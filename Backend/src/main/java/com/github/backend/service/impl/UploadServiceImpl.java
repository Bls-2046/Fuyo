package com.github.backend.service.impl;

import com.github.backend.dto.user.ScheduleRequest;
import com.github.backend.entity.ScheduleEntity;
import com.github.backend.entity.UserEntity;
import com.github.backend.repository.ScheduleRepository;
import com.github.backend.repository.UserRepository;
import com.github.backend.repository.WeChatUserRepository;
import com.github.backend.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户上传信息的逻辑处理
 */
@Service
public class UploadServiceImpl implements UploadService {
    private final WeChatUserRepository weChatUserRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public UploadServiceImpl(WeChatUserRepository weChatUserRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.weChatUserRepository = weChatUserRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
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

        ScheduleEntity schedule = new ScheduleEntity();

        schedule.setTitle(scheduleRequest.getSchedule().getTitle());
        schedule.setDatetime(scheduleRequest.getSchedule().getDateTime());
        schedule.setReminderDatetime(scheduleRequest.getSchedule().getReminderDatetime());
        schedule.setDescription(scheduleRequest.getSchedule().getDescription());
        schedule.setUserEntity(userRepository.findByUsername(scheduleRequest.getUsername()));

        scheduleRepository.save(schedule);

        return false;
    }
}
