package com.github.fuyo.controller;

import com.github.fuyo.entity.NaviFunctionButtonEnum;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.model.NavigationModel;
import com.github.fuyo.model.UserViewModel;
import com.github.fuyo.view.navigation.NavigationView;
import com.github.fuyo.view.navigation.clazz.ClazzView;
import com.github.fuyo.view.navigation.user.UserView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.List;

@Slf4j
public class UserViewController {
    @Getter
    private final UserView view;
    private final UserViewModel model;

    public UserViewController(UserView view, UserViewModel model) {
        this.view = view;
        this.model = model;

        // Button Object
        JButton switchAccountBtn = view.getSwitchAccountButton();
        switchAccountBtn.addActionListener(e -> {
            log.info("Switch Account Button clicked");
        });

    }
}
