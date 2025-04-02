package com.github.fuyo.model;

import com.github.dto.schedule.*;
import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ScheduleModel {
    public ScheduleModel() {}

    /**
     * 获取用户日志信息
     * @param username 用户名
     */
    public static void fetchSchedule(String username) {
        String url = "http://localhost:8080/api/fetch/schedule";
        FetchScheduleRequest fetchScheduleRequest = new FetchScheduleRequest();
        try {
            fetchScheduleRequest.setUsername(username);

            FetchScheduleResponse fetchScheduleResponse = Https.post(url, fetchScheduleRequest, null, FetchScheduleResponse.class);
            List<ScheduleEntity> scheduleEntity = fetchScheduleResponse.getSchedule().stream()
                    .map(responseSchedule -> new ScheduleEntity(
                            responseSchedule.getId(),
                            responseSchedule.getTitle(),
                            responseSchedule.getDateTime(),
                            responseSchedule.getReminderDateTime(),
                            responseSchedule.getDescription(),
                            responseSchedule.getIsReminderInClient()
                    ))
                    .collect(Collectors.toList());

            log.info("成功获取用户日志信息: {}", scheduleEntity);

            // 使用同步块确保线程安全
            synchronized (UserEntity.getUserInformation()) {
                UserEntity.getUserInformation().setSchedule(scheduleEntity);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 添加用户日程信息
     * @param schedule 日程信息
     * @return String
     */
    public String addSchedule(ScheduleEntity schedule) {
        if (schedule.getTitle() == null || schedule.getTitle().isEmpty() || schedule.getDateTime() == null) {
            return "请填写标题及日期时间";
        }

        // 判断时间是否早于当前时间
        LocalDateTime now = LocalDateTime.now();
        boolean isYear = (schedule.getDateTime().getYear() >= now.getYear());
        boolean isMonth = (schedule.getDateTime().getMonthValue() >= now.getMonthValue());
        boolean isDay = (schedule.getDateTime().getDayOfMonth() >= now.getDayOfMonth());
        boolean isHour = (schedule.getDateTime().getHour() >= now.getHour());
        boolean isMinute = (schedule.getDateTime().getMinute() >= now.getMinute());

        if (!isYear || !isMonth || !isDay || !isHour || !isMinute) {
            return "昨天的事情不可以明天做";
        }

        String result = "添加失败";
        try {
            String url = "http://127.0.0.1:8080/api/update/schedule/add";
            AddScheduleRequest addScheduleRequest = new AddScheduleRequest();
            AddScheduleRequest.RequestSchedule newSchedule = new AddScheduleRequest.RequestSchedule();

            addScheduleRequest.setUsername(UserEntity.getUserInformation().getUsername());
            addScheduleRequest.setNickname(UserEntity.getUserInformation().getWechatUser().getNickname());

            newSchedule.setTitle(schedule.getTitle());
            newSchedule.setDateTime(schedule.getDateTime());
            newSchedule.setReminderDateTime(schedule.getDateTime());
            newSchedule.setDescription(schedule.getDescription());

            addScheduleRequest.setSchedule(newSchedule);

            AddScheduleResponse addResult = Https.post(url, addScheduleRequest, null, AddScheduleResponse.class);

            if (addResult.getStatus() == 200) {
                result = "添加成功";
            }
        }  catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 删除用户指定的日程信息
     * @param schedule 日程信息
     * @return String
     */
    public static String deleteSchedule(ScheduleEntity schedule) {
        String url = "http://127.0.0.1:8080/api/update/schedule/delete";
        DeleteScheduleRequest deleteScheduleRequest = new DeleteScheduleRequest();

        deleteScheduleRequest.setId(schedule.getId());
        deleteScheduleRequest.setUsername(UserEntity.getUserInformation().getUsername());

        try {
            DeleteScheduleResponse deleteResult = Https.delete(url, deleteScheduleRequest, null, DeleteScheduleResponse.class);

            if (deleteResult.getStatus() == 200) {
                return "删除成功";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "删除失败";
    }
}
