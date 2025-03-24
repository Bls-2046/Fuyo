package com.github.backend.service.impl;

import com.github.backend.dto.user.ScheduleRequest;
import com.github.backend.entity.ScheduleEntity;
import com.github.backend.entity.UserEntity;
import com.github.backend.repository.ScheduleRepository;
import com.github.backend.repository.UserRepository;
import com.github.backend.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * 保存新的日程信息
     * @param schedule
     */
    @Override
    public Boolean addScheduleInfo(ScheduleRequest schedule) {
        ScheduleEntity newSchedule = new ScheduleEntity();

        String username = schedule.getUsername();
        UserEntity user = userRepository.findByUsername(username);

        String openid = schedule.getOpenid();
        String title = schedule.getSchedule().getTitle();
        LocalDateTime dateTime = schedule.getSchedule().getDateTime();
        LocalDateTime reminderDatetime = schedule.getSchedule().getReminderDatetime();
        String description = schedule.getSchedule().getDescription();

        if (username != null && openid != null && title != null && dateTime != null && reminderDatetime != null) {
            newSchedule.setTitle(title);
            newSchedule.setDatetime(dateTime);
            newSchedule.setReminderDatetime(reminderDatetime);
            if (description != null) {
                newSchedule.setDescription(description);
            }
            newSchedule.setUserEntity(user);

            scheduleRepository.save(newSchedule);

            return true;
        }
        return false;
    }
}
