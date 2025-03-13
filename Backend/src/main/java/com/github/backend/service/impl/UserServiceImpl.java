package com.github.backend.service.impl;

import com.github.backend.mapper.UserMapper;
import com.github.backend.model.User;
import com.github.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User result = userMapper.getUserInfoByUser(username);
        if (result == null) {
            // 脚本
            log.info("result is null for username {}", username);
            return null;
        } else {
            log.info("username {} login success", username);
            return result;
        }
    }
}
