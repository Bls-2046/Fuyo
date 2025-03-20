package com.github.fuyo.controller;

import com.github.fuyo.entity.NaviFunctionButtonEnum;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.model.NavigationModel;
import com.github.fuyo.view.navigation.NavigationView;
import com.github.fuyo.view.navigation.clazz.ClazzView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.List;

@Slf4j
public class NavigationController {
    @Getter
    private final NavigationView view;
    private final NavigationModel model;

    public NavigationController(NavigationView view, NavigationModel model) {
        this.view = view;
        this.model = model;

        // 获取用户
        UserEntity user = UserEntity.getUserInformation();

        // 按钮对象
        List<JButton> naviButtonList = view.getNaviButtonList();

        // 获取退出按钮对象 若要获取其他对象，请修改为其他ENUM值
        JButton exitButton = naviButtonList.get(NaviFunctionButtonEnum.EXIT.ordinal());
        // 添加监听
        exitButton.addActionListener(e -> System.exit(0));

        // Clazz层
        JButton clazzButton  = naviButtonList.get(NaviFunctionButtonEnum.CLAZZ.ordinal());
        clazzButton.addActionListener(e -> {
            List<UserEntity.Tabletime> tabletime = user.getTabletime();

            // 按图层顺序渲染，优先渲染导航栏。
            SwingUtilities.invokeLater(() -> {
                view.renderRouterView(new ClazzView(tabletime));
            });
        });
    }
}
