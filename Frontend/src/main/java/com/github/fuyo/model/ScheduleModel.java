package com.github.fuyo.model;

import com.github.fuyo.dto.schedule.AddScheduleResponse;
import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ScheduleModel {
    public ScheduleModel() {}

    public String addSchedule(ScheduleEntity schedule) {
        if (schedule.getTitle() == null || schedule.getTitle().isEmpty() || schedule.getDatetime() == null) {
            return "请填写标题及日期时间";
        }

        // 判断时间是否早于当前时间
        LocalDateTime now = LocalDateTime.now();
        boolean isYear = (schedule.getDatetime().getYear() >= now.getYear());
        boolean isMonth = (schedule.getDatetime().getMonthValue() >= now.getMonthValue());
        boolean isDay = (schedule.getDatetime().getDayOfMonth() >= now.getDayOfMonth());
        boolean isHour = (schedule.getDatetime().getHour() >= now.getHour());
        boolean isMinute = (schedule.getDatetime().getMinute() >= now.getMinute());

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
            scheduleObject.put("dateTime", schedule.getDatetime());
            scheduleObject.put("reminderDateTime", schedule.getReminderDatetime());
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

    public String deleteSchedule(ScheduleEntity schedule) {
        String url = "http://127.0.0.1:8080/api/update/schedule/delete";
        return null;
    }
}
