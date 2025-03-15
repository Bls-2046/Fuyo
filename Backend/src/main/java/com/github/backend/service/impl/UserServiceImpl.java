package com.github.backend.service.impl;

import com.github.backend.entity.User;
import com.github.backend.repository.TabletimeRepository;
import com.github.backend.repository.UserRepository;
import com.github.backend.service.UserService;
import com.github.backend.utils.Https;
import com.github.backend.utils.Password;
import com.github.backend.utils.PythonScript;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TabletimeRepository tabletimeRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TabletimeRepository tabletimeRepository) {
        this.userRepository = userRepository;
        this.tabletimeRepository = tabletimeRepository;
    }

    @Override
    public void loginVerification(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            try {
                // 运行 Python 脚本进行验证并获取用户信息
                String loginScriptResult = PythonScript.executePythonScript(username, password);
                log.info("Run Python script");

                // 解析 Python 脚本返回的 JSON 字符串
                JSONObject jsonObject = new JSONObject(loginScriptResult);

                // 获取 message 和 data
                String message = jsonObject.getString("message");
                JSONObject data = jsonObject.getJSONObject("data");

                // 根据 message 判断验证结果
                if (!message.equals("登录成功")) {
                    throw new RuntimeException(message);
                }

                log.info(message);
                log.info(data.toString());
                // 将数据保持到数据库
                saveUser(data, username, password);

            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            if (!Password.matches(password, user.getPassword())) {
                throw new RuntimeException("密码错误");
            }
        }
    }

    public void saveUser(JSONObject data, String username, String password) {
        User user = new User();
        try {
            user.setId(username);
            user.setUsername(username);
            user.setPassword(Password.encodePassword(password));
            user.setName(data.getString("姓名"));
            user.setEmail(data.getString("邮箱"));
            user.setDepartment(data.getString("部门"));

            // 提取手机号码中的数字部分
            String phoneFromData = data.getString("手机");
            String phone = phoneFromData.replaceAll("[^0-9]", ""); // 只保留数字
            user.setPhone(phone);

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
            user.setCookie(cookie);
            userRepository.save(user);

            // 使用 cookie 发起请求，获取课表信息并存入数据库
            fetchAndSaveTabletime(user, cookie);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void fetchAndSaveTabletime(User user, String cookie) {
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Cookie", cookie);

            // 设置请求体（如果需要）
            JSONObject body = new JSONObject();

            // 使用 Https 类发送 POST 请求获取课表信息
            JSONArray rawResponse = (JSONArray) Https.post("https://s.bitzh.edu.cn/manage/protal/gettabletime", body, headers);

            // 打印原始响应数据
            log.info("课表 API 返回的原始数据: {}", rawResponse);

            // 直接处理 JSONArray
            for (int i = 0; i < rawResponse.length(); i++) {
                JSONObject course = rawResponse.getJSONObject(i);

                // 创建并保存课表信息
                User.Tabletime tabletime = new User.Tabletime();
                tabletime.setId(user.getId());
                tabletime.setX(course.optInt("x", 0));
                tabletime.setY(course.optInt("y", 0));
                tabletime.setValue(course.optString("value", ""));
                tabletime.setUser(user);

                tabletimeRepository.save(tabletime);
            }
            log.info("用户 {} 的课表信息已成功保存", user.getUsername());
        } catch (Exception e) {
            log.error("获取或保存课表信息失败: {}", e.getMessage());
        }
    }
}
