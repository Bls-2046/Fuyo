package com.github.backend.service.impl;

import com.github.backend.dto.schedule.AddScheduleRequest;
import com.github.backend.dto.schedule.DeleteScheduleRequest;
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

        String username = schedule.getUsername() ;
        UserEntity user = userRepository.findByUsername(username);

        String openid = schedule.getOpenid();
        String title = schedule.getSchedule().getTitle();
        LocalDateTime dateTime = schedule.getSchedule().getDateTime();
        LocalDateTime reminderDateTime = schedule.getSchedule().getReminderDateTime();
        String description = schedule.getSchedule().getDescription();

//        if (username != null && openid != null && title != null && dateTime != null && reminderDateTime != null) {
            newSchedule.setTitle(title);
            newSchedule.setDateTime(dateTime);
            newSchedule.setReminderDateTime(reminderDateTime);
            newSchedule.setDescription(description);
            newSchedule.setUserEntity(user);
            newSchedule.setOpenid(openid);
            newSchedule.setIsReminderInClient(false);
            newSchedule.setIsSendWeChatReminder(false);

            scheduleRepository.save(newSchedule);

            return true;
//        }
//        return false;
    }

    /**
     * 删除指定日程信息
     * @param deleteScheduleRequest 日程信息
     * @return Boolean
     */
    @Override
    public Boolean deleteScheduleInfo(DeleteScheduleRequest deleteScheduleRequest) {

        try {
            String username = deleteScheduleRequest.getUsername();
            String openid = deleteScheduleRequest.getOpenid();
            String title = deleteScheduleRequest.getSchedule().getTitle();
            LocalDateTime dateTime = deleteScheduleRequest.getSchedule().getDateTime().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime reminderDateTime = deleteScheduleRequest.getSchedule().getReminderDateTime().truncatedTo(ChronoUnit.SECONDS);  // 修正此处为 getReminderDateTime()

            String description = deleteScheduleRequest.getSchedule().getDescription();

            ScheduleEntity deleteSchedule = scheduleRepository.findByTitleAndDateTimeAndReminderDateTimeAndDescriptionAndOpenidAndUserEntityUsername(
                    title,
                    dateTime.minusSeconds(1),  // 时间下限（当前时间-1秒）
                    dateTime.plusSeconds(1),   // 时间上限（当前时间+1秒）
                    reminderDateTime.minusSeconds(1),
                    reminderDateTime.plusSeconds(1),
                    description,
                    openid,
                    username
            );

            scheduleRepository.delete(deleteSchedule);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
