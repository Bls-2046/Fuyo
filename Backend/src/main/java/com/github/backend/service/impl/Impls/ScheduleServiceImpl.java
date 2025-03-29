package com.github.backend.service.impl.Impls;

import com.github.backend.dto.schedule.*;
import com.github.backend.entity.ScheduleEntity;
import com.github.backend.entity.UserEntity;
import com.github.backend.repository.ScheduleRepository;
import com.github.backend.repository.UserRepository;
import com.github.backend.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
     * 返回用户的日程安排信息
     * @param fetchScheduleRequest 用户名
     * @return List<ScheduleResponse.Schedule>
     */
    @Override
    public List<FetchScheduleResponse.Schedule> fetchSchedule(FetchScheduleRequest fetchScheduleRequest) {
        String username = fetchScheduleRequest.getUsername();

        List<ScheduleEntity> querySchedule =  scheduleRepository.findByUserEntityUsername(username);
        List<FetchScheduleResponse.Schedule> scheduleList = new ArrayList<>();

        // LocalDateTime now = LocalDateTime.now();

        for (ScheduleEntity schedule : querySchedule) {

            if (schedule.getIsReminderInClient() == Boolean.FALSE) {
                FetchScheduleResponse.Schedule scheduleResponseSchedule = new FetchScheduleResponse.Schedule();

                scheduleResponseSchedule.setId(schedule.getId());
                scheduleResponseSchedule.setTitle(schedule.getTitle());
                scheduleResponseSchedule.setDateTime(schedule.getDateTime());
                scheduleResponseSchedule.setReminderDateTime(schedule.getReminderDateTime());
                scheduleResponseSchedule.setDescription(schedule.getDescription());
                scheduleResponseSchedule.setIsReminderInClient(schedule.getIsReminderInClient());

                scheduleList.add(scheduleResponseSchedule);
            }
        }
        scheduleList.sort(Comparator.comparing(FetchScheduleResponse.Schedule::getReminderDateTime));

        return scheduleList;
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
            String nickname = schedule.getNickname();
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
            newSchedule.setOpenid(nickname);
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
     * @param markRemindedScheduleForClientRequest 日程信息
     * @return Boolean
     */
    @Override
    public Boolean markRemindedScheduleForClient(MarkRemindedScheduleForClientRequest markRemindedScheduleForClientRequest) {
        try {
            String id = markRemindedScheduleForClientRequest.getId();
            String username = markRemindedScheduleForClientRequest.getUsername();

            ScheduleEntity schedule = scheduleRepository.findByIdAndUserEntityUsername(id, username);

            schedule.setIsReminderInClient(Boolean.TRUE);
            scheduleRepository.save(schedule);

            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
