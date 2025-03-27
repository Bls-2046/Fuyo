package com.github.backend.service.impl;

import com.github.backend.dto.schedule.AddScheduleRequest;
import com.github.backend.dto.schedule.DeleteScheduleRequest;
import com.github.backend.dto.schedule.MarkRemindedScheduleForClientRequest;
import com.github.backend.entity.ScheduleEntity;
import com.github.backend.entity.UserEntity;
import com.github.backend.repository.ScheduleRepository;
import com.github.backend.repository.UserRepository;
import com.github.backend.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * 保存新的日程信息
     * @param schedule 添加日程信息
     * @return Boolean
     */
    @Override
    public Boolean addScheduleInfo(AddScheduleRequest schedule) {
        ScheduleEntity newSchedule = new ScheduleEntity();

        try {
            String username = schedule.getUsername();
            UserEntity user = userRepository.findByUsername(username);
            String openid = schedule.getOpenid();
            String title = schedule.getSchedule().getTitle();
            LocalDateTime dateTime = schedule.getSchedule().getDateTime();
            LocalDateTime reminderDateTime = schedule.getSchedule().getReminderDateTime();
            String description = schedule.getSchedule().getDescription();

            newSchedule.setTitle(title);
            newSchedule.setDateTime(dateTime);
            newSchedule.setReminderDateTime(reminderDateTime);
            if (Objects.equals(description, "")) {
                newSchedule.setDescription("好像有什么重要的事... ");
            } else {
                newSchedule.setDescription(description);
            }
            newSchedule.setUserEntity(user);
            newSchedule.setOpenid(openid);
            newSchedule.setIsReminderInClient(Boolean.FALSE);
            newSchedule.setIsSendWeChatReminder(Boolean.FALSE);

            scheduleRepository.save(newSchedule);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * 删除指定日程信息
     * @param deleteScheduleRequest 日程信息
     * @return Boolean
     */
    @Override
    public Boolean deleteScheduleInfo(DeleteScheduleRequest deleteScheduleRequest) {
        try {
            String id = deleteScheduleRequest.getId();
            String username = deleteScheduleRequest.getUsername();

            ScheduleEntity deleteSchedule = scheduleRepository.findByIdAndUserEntityUsername(id, username);

            scheduleRepository.delete(deleteSchedule);

            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * 为前端已显示提醒弹窗的日程做标记
     * @param markRemindedScheduleForClientRequest
     * @return Boolean
     */
    @Override
    public Boolean markRemindedScheduleForClient(MarkRemindedScheduleForClientRequest markRemindedScheduleForClientRequest) {
        try {
            String id = markRemindedScheduleForClientRequest.getId();
            String username = markRemindedScheduleForClientRequest.getUsername();

            ScheduleEntity schedule = scheduleRepository.findByIdAndUserEntityUsername(id, username);

            log.info(schedule.toString());

            schedule.setIsReminderInClient(Boolean.TRUE);
            scheduleRepository.save(schedule);

            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
