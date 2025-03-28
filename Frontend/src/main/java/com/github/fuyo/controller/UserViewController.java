package com.github.fuyo.controller;

import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.listener.NavigationCloseListener;
import com.github.fuyo.listener.ScheduleListener;
import com.github.fuyo.model.UserViewModel;
import com.github.fuyo.view.navigation.user.UserView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class UserViewController {
    @Getter
    private final UserView view;
    private final UserViewModel model;
    private final NavigationCloseListener navigationCloseListener;

    public UserViewController(UserView view, UserViewModel model, NavigationCloseListener navigationCloseListener) {
        this.view = view;
        this.model = model;
        this.navigationCloseListener = navigationCloseListener;

        // Button Object
        JButton switchAccountBtn = view.getSwitchAccountButton();
        switchAccountBtn.addActionListener(e -> {
            switchAccount();
        });
    }

    // 切换账号
    public void switchAccount() {
        try {
            // 清空用户名密码缓存文件
            model.clearFileContent();
            // 清空当前用户信息
            UserEntity.clearUserInformation();
            ScheduleListener.stop();
            // 关闭导航界面
            navigationCloseListener.onClose();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
