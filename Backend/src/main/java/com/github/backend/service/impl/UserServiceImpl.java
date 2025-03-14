package com.github.backend.service.impl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.backend.mapper.UserMapper;
import com.github.backend.entity.User;
import com.github.backend.repository.UserRepository;
import com.github.backend.service.UserService;
import com.github.backend.utils.PythonScript;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            try {
                log.info("Run Python script");

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

                log.info(loginScriptResult);

                return null;
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            if (!user.getPassword().equals(password)) {
                throw new RuntimeException("密码错误");
            } else {
                return user;
            }
        }
        return null;
    }
}
