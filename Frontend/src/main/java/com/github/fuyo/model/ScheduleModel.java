package com.github.fuyo.model;

import com.github.fuyo.dto.schedule.AddScheduleResponse;
import com.github.fuyo.dto.schedule.DeleteScheduleResponse;
import com.github.fuyo.dto.schedule.ScheduleRequest;
import com.github.fuyo.dto.schedule.ScheduleResponse;
import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ScheduleModel {
    public ScheduleModel() {}

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
            Map<String, Object> body = new HashMap<>();

            body.put("username", UserEntity.getUserInformation().getUsername());
            // body.put("openid", UserEntity.getUserInformation().getWechatUser().getOpenid());

            // 测试
            body.put("openid", "textUser");

            Map<String, Object> scheduleObject = new HashMap<>();
            scheduleObject.put("title", schedule.getTitle());
            scheduleObject.put("dateTime", schedule.getDateTime());
            scheduleObject.put("reminderDateTime", schedule.getReminderDateTime());
            scheduleObject.put("description", schedule.getDescription());

            // 将 schedule 对象放入外层对象
            body.put("schedule", scheduleObject);

            AddScheduleResponse addResult = Https.<AddScheduleResponse>post(url, body, null, AddScheduleResponse.class);

            if (addResult.getStatus() == 200) {
                result = "添加成功";
            }
        } catch (IOException e) {
            log.info(e.getMessage());
            result = "连接超时, 请稍后重试";
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
        Map<String, Object> body = new HashMap<>();

        body.put("username", UserEntity.getUserInformation().getUsername());

        body.put("openid", "textUser");

        Map<String, Object> scheduleObject = new HashMap<>();
        scheduleObject.put("title", schedule.getTitle());
        scheduleObject.put("dateTime", schedule.getDateTime());
        scheduleObject.put("reminderDateTime", schedule.getReminderDateTime());
        scheduleObject.put("description", schedule.getDescription());

        // 将 schedule 对象放入外层对象
        body.put("schedule", scheduleObject);
        try {
            DeleteScheduleResponse deleteResult = Https.<DeleteScheduleResponse>post(url, body, null, DeleteScheduleResponse.class);

            if (deleteResult.getStatus() == 200) {
                return "删除成功";
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return "删除失败";
    }

    public static void getSchedule(String username) {
        String url = "http://localhost:8080/api/user/schedule";
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        try {
            scheduleRequest.setUsername(username);
//                        scheduleRequest.setOpenid(openid);

            // TODO 测试数据
            scheduleRequest.setOpenid("textUer");

            ScheduleResponse scheduleResponse = Https.<ScheduleResponse>post(url, scheduleRequest, null, ScheduleResponse.class);
            List<ScheduleEntity> scheduleEntity = scheduleResponse.getSchedule().stream()
                    .map(responseSchedule -> new ScheduleEntity(
                            responseSchedule.getTitle(),
                            responseSchedule.getDateTime(),
                            responseSchedule.getReminderDateTime(),
                            responseSchedule.getDescription(),
                            responseSchedule.getIsReminderInClient()
                    ))
                    .collect(Collectors.toList());

            log.info(scheduleEntity.toString());

            // 使用同步块确保线程安全
            synchronized (UserEntity.getUserInformation()) {
                UserEntity.getUserInformation().setSchedule(scheduleEntity);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
