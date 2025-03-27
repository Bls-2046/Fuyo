package com.github.backend.service.impl;

import com.github.backend.dto.schedule.Schedule;
import com.github.backend.dto.schedule.ScheduleResponse;
import com.github.backend.dto.user.TabletimeResponse;
import com.github.backend.dto.user.UserInformationResponse;
import com.github.backend.entity.ScheduleEntity;
import com.github.backend.entity.TabletimeEntity;
import com.github.backend.entity.UserEntity;
import com.github.backend.repository.ScheduleRepository;
import com.github.backend.repository.TabletimeRepository;
import com.github.backend.repository.UserRepository;
import com.github.backend.service.UserService;
import com.github.backend.utils.Https;
import com.github.backend.utils.Password;
import com.github.backend.utils.PythonScript;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对用户基本信息进行处理
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TabletimeRepository tabletimeRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TabletimeRepository tabletimeRepository, ScheduleRepository scheduleRepository) {
        this.userRepository = userRepository;
        this.tabletimeRepository = tabletimeRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * 用户登录验证
     * 通过 python 脚本 login_bitzh.py 模拟账号登录
     * @param username 用于登录的用户名
     * @param password 用于登录的密码
     */
    @Override
    public Boolean loginVerification(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            try {
                // 运行 Python 脚本进行验证并获取用户信息
                String loginScriptResult = PythonScript.executePythonScript(username, password);

                // 解析 Python 脚本返回的 JSON 字符串
                JSONObject jsonObject = new JSONObject(loginScriptResult);

                // 获取 message 和 data
                String message = jsonObject.getString("message");
                JSONObject data = jsonObject.getJSONObject("data");

                // 根据 message 判断验证结果
                if (!message.equals("登录成功")) {
                    throw new RuntimeException(message);
                }

                // 将数据保持到数据库
                saveUserInformation(data, username, password);

                return true;
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            if (!Password.matches(password, userEntity.getPassword())) {
                throw new RuntimeException("密码错误");
            }
            return true;
        }
        return false;
    }

    /**
     * 获取用户基本信息
     * @param username 用户名用于查询
     * @return 返回用户基本信息
     */
    @Override
    public UserInformationResponse.UserInformation getUserInformation(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            return null;
        }
        UserInformationResponse.UserInformation userInfo = new UserInformationResponse.UserInformation();

        userInfo.setUsername(userEntity.getUsername());
        userInfo.setName(userEntity.getName());
        userInfo.setDepartment(userEntity.getDepartment());
        userInfo.setEmail(userEntity.getEmail());
        userInfo.setPhone(userEntity.getPhone());

        return userInfo;
    }



    /**
     * 获取用户当天课表信息
     * @param username 用户名用于查询
     * @return 返回当天课表
     */
    @Override
    public List<TabletimeResponse.Tabletime> getTabletime(String username) {

        // 计算是第几周的第几天
        LocalDate firstDay = LocalDate.of(2025, 2, 23);
        // 获取当前日期
         LocalDate today = LocalDate.now();
        // 计算从第一周到今天的天数
        long daysBetween = ChronoUnit.DAYS.between(firstDay, today);
        // 计算当前是第几周（向上取整）

        int currentWeek = (int) Math.ceil((double) daysBetween / 7);
        // 如果为双周
        String weekType = (currentWeek % 2 == 0 ? "双周" : "单周");
        // 计算当前是第几周的第几天（1到7）
        int dayOfWeek = (int) (daysBetween % 7 == 0 ?  7 : daysBetween % 7);

        List<TabletimeEntity> queryTabletime = tabletimeRepository.findByUserEntityUsernameAndX(username, dayOfWeek);

        // 创建 tabletime 列表
        List<TabletimeResponse.Tabletime> tabletimeList = new ArrayList<>();

        // 遍历 queryTabletime, 将每个对象转换为 TabletimeResponse.Tabletime
        for (TabletimeEntity tabletime : queryTabletime) {
            TabletimeResponse.Tabletime tabletimeResponseTabletime = new TabletimeResponse.Tabletime();
            if (tabletime.getWeekType() != null) {
                if (!tabletime.getWeekType().equals(weekType)) {
                    continue;
                }
            }
            if (tabletime.getStartWeek() <= currentWeek && tabletime.getFinishWeek() >= currentWeek) {

                tabletimeResponseTabletime.setKeyID(tabletime.getKeyID());
                tabletimeResponseTabletime.setClazz(tabletime.getClazz());
                tabletimeResponseTabletime.setX(tabletime.getX());
                tabletimeResponseTabletime.setY(tabletime.getY());
                tabletimeResponseTabletime.setBeginDay(tabletime.getBeginDay());
                tabletimeResponseTabletime.setEndDay(tabletime.getEndDay());
                tabletimeResponseTabletime.setWeekType(tabletime.getWeekType());
                tabletimeResponseTabletime.setPlace(tabletime.getPlace());
                tabletimeResponseTabletime.setStartWeek(tabletime.getStartWeek());
                tabletimeResponseTabletime.setFinishWeek(tabletime.getFinishWeek());

                // 添加到 tabletime 列表
                tabletimeList.add(tabletimeResponseTabletime);
            }
        }
        tabletimeList.sort(Comparator.comparingInt(TabletimeResponse.Tabletime::getY));

        log.info(tabletimeList.toString());

        return tabletimeList;
    }

    /**
     * 保存用户数据到数据库
     * @param data 返回的数据
     * @param username 用户名
     * @param password 密码
     */
    public void saveUserInformation(JSONObject data, String username, String password) {
        UserEntity userEntity = new UserEntity();
        try {
            userEntity.setId(username);
            userEntity.setUsername(username);
            userEntity.setPassword(Password.encodePassword(password));
            userEntity.setName(data.getString("姓名"));
            userEntity.setEmail(data.getString("邮箱"));
            userEntity.setDepartment(data.getString("部门"));

            // 提取手机号码中的数字部分
            String phoneFromData = data.getString("手机");
            String phone = phoneFromData.replaceAll("[^0-9]", ""); // 只保留数字
            userEntity.setPhone(phone);

            // 解析并设置 cookie
            JSONArray cookieArray = data.getJSONArray("cookie");
            StringBuilder cookieBuilder = new StringBuilder();
            // 拼接
            for (int i = 0; i < cookieArray.length(); i++) {
                JSONObject cookieObj = cookieArray.getJSONObject(i);
                String name = cookieObj.getString("name");
                String value = cookieObj.getString("value");
                cookieBuilder.append(name).append("=").append(value);
            }
            String cookie = cookieBuilder.toString();
            userEntity.setCookie(cookie);

            userRepository.save(userEntity);

            // 使用 cookie 发起请求，获取课表信息并存入数据库
            fetchAndSaveTabletime(userEntity, cookie);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取并保存用户课表信息
     * @param userEntity 用户实体类
     * @param cookie cookie 用于对第一次对 <a href="https://s.bitzh.edu.cn/manage/protal/gettabletime" /> 发起请求
     */
    private void fetchAndSaveTabletime(UserEntity userEntity, String cookie) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookie);

            JSONArray mergedResponse = new JSONArray();

            for (int zc = 0; zc <= 20; zc++) {
                // 构造表单数据
                Map<String, String> formData = new HashMap<>();
                formData.put("zc", String.valueOf(zc));

                // 发送 POST 请求
                JSONArray response = (JSONArray) Https.post("https://s.bitzh.edu.cn/manage/protal/gettabletime", formData, headers);

                // 将响应数据合并到 mergedResponse
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        mergedResponse = mergeAndDeduplicate(mergedResponse, response);
                    }
                }
            }

            // 直接处理 JSONArray
            for (int i = 0; i < mergedResponse.length(); i++) {

                JSONObject clazz = mergedResponse.getJSONObject(i);

                // 创建并保存课表信息
                TabletimeEntity tabletime = new TabletimeEntity();
                // 对课程信息字段进行拆分
                ClazzInformation clazzInformation = ClazzInformation.handleClazzInformation(clazz.optString("value"));

                tabletime.setClazz(clazzInformation.getClazz());
                tabletime.setX(clazz.optInt("x", 0));
                tabletime.setY(clazz.optInt("y", 0));
                tabletime.setBeginDay(clazzInformation.getBeginDay());
                tabletime.setEndDay(clazzInformation.getEndDay());
                tabletime.setWeekType(clazzInformation.getWeekType());
                tabletime.setPlace(clazzInformation.getPlace());
                tabletime.setStartWeek(clazzInformation.getStartWeek());
                tabletime.setFinishWeek(clazzInformation.getFinishWeek());

                tabletime.setUserEntity(userEntity);

                tabletimeRepository.save(tabletime);
            }
            log.info("用户 {} 的课表信息已成功保存", userEntity.getUsername());
        } catch (Exception e) {
            log.error("获取或保存课表信息失败: {}", e.getMessage());
        }
    }

    /**
     * 合并两个 JSONArray 并根据 x、y 和 value 去重，同时保留单周和双周的课程
     *
     * @param arr1 第一个 JSONArray
     * @param arr2 第二个 JSONArray
     * @return 去重后的 JSONArray
     */
    public static JSONArray mergeAndDeduplicate(JSONArray arr1, JSONArray arr2) {
        // 使用 Map 存储唯一键和对应的 JSONObject
        Map<String, JSONObject> uniqueMap = new HashMap<>();

        // 添加 arr1 的数据
        if (arr1 != null) {
            for (int i = 0; i < arr1.length(); i++) {
                JSONObject item = arr1.getJSONObject(i);
                String key = generateUniqueKey(item);
                uniqueMap.putIfAbsent(key, item); // 如果不存在则添加
            }
        }

        // 添加 arr2 的数据
        if (arr2 != null) {
            for (int i = 0; i < arr2.length(); i++) {
                JSONObject item = arr2.getJSONObject(i);
                String key = generateUniqueKey(item);
                uniqueMap.putIfAbsent(key, item); // 如果不存在则添加
            }
        }

        // 将去重后的数据放入 JSONArray
        JSONArray mergedResponse = new JSONArray();
        for (JSONObject item : uniqueMap.values()) {
            mergedResponse.put(item);
        }

        return mergedResponse;
    }

    /**
     * 根据 x 和 value 生成唯一键
     * @param item JSONObject 对象
     * @return 唯一键
     */
    private static String generateUniqueKey(JSONObject item) {
        int x = item.getInt("x");
        String value = item.getString("value");

        return x + "|" + value;
    }


    @Data
    private static class ClazzInformation {
        private String clazz;        // 课程名称
        private int beginDay;       // 开始天
        private int endDay;         // 结束天
        private String weekType;    // 周类型（单周、双周）
        private String place;       // 地点
        private int startWeek;      // 开始周
        private int finishWeek;     // 结束周

        // 全参构造函数
        public ClazzInformation(String clazz, int startWeek, int finishWeek, String weekType, String place, int beginDay, int endDay) {
            this.clazz = clazz;
            this.startWeek = startWeek;
            this.finishWeek = finishWeek;
            this.weekType = weekType;
            this.place = place;
            this.beginDay = beginDay;
            this.endDay = endDay;
        }

        /**
         * 拆分课程字符串
         * @param tabletimeData 课程信息
         * @return ClazzInformation
         */
        public static ClazzInformation handleClazzInformation(String tabletimeData) {
            String clazz;
            int beginDay;
            int endDay;
            String weekType;
            String place;
            int startWeek;
            int finishWeek;

            // 正则表达式模式
            String regex = "(?<clazz>.+?)<br/>(?:(?<startWeek>\\d+)-(?<finishWeek>\\d+)周|(?<singleWeek>\\d+)周)(?:（(?<weekType>单周|双周)）)?<br/>(?<place>.+?)【(?<timeRanges>.+)】";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(tabletimeData);

            if (matcher.find()) {
                clazz = matcher.group("clazz");

                // 处理单周格式（如 "3周"）
                if (matcher.group("singleWeek") != null) {
                    startWeek = Integer.parseInt(matcher.group("singleWeek"));
                    finishWeek = startWeek;
                } else {
                    startWeek = Integer.parseInt(matcher.group("startWeek"));
                    finishWeek = Integer.parseInt(matcher.group("finishWeek"));
                }

                weekType = matcher.group("weekType"); // 可能为 null
                place = matcher.group("place");

                // 处理时间范围（如 "11-12-13" 或 "07-08-09-10"）
                String timeRanges = matcher.group("timeRanges");
                String[] times = timeRanges.split("-");
                beginDay = Integer.parseInt(times[0]);
                endDay = Integer.parseInt(times[times.length - 1]);

                // 返回 ClazzInformation 对象
                return new ClazzInformation(clazz, startWeek, finishWeek, weekType, place, beginDay, endDay);
            }

            // 如果没有匹配到数据，抛出异常
            throw new IllegalArgumentException("未找到匹配的课程信息");
        }
    }

    /**
     * 返回用户的日程安排信息
     * @param username 用户名
     * @return List<ScheduleResponse.Schedule>
     */
    @Override
    public List<ScheduleResponse.Schedule> getSchedule(String username) {
        List<ScheduleEntity> querySchedule =  scheduleRepository.findByUserEntityUsername(username);
        List<ScheduleResponse.Schedule> scheduleList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (ScheduleEntity schedule : querySchedule) {
            if (schedule.getReminderDateTime().isBefore(now)) {
                ScheduleResponse.Schedule scheduleResponseSchedule = new ScheduleResponse.Schedule();

                scheduleResponseSchedule.setTitle(schedule.getTitle());
                scheduleResponseSchedule.setDateTime(schedule.getDateTime());
                scheduleResponseSchedule.setReminderDateTime(schedule.getReminderDateTime());
                scheduleResponseSchedule.setDescription(schedule.getDescription());
                scheduleResponseSchedule.setIsReminderInClient(schedule.getIsReminderInClient());

                scheduleList.add(scheduleResponseSchedule);
            }
        }
        return scheduleList;
    }
}
