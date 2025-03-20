package com.github.fuyo.controller;

import com.github.fuyo.entity.NaviFunctionButtonEnum;
import com.github.fuyo.entity.NaviFunctionEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.view.navigation.NavigationView;
import com.github.fuyo.view.navigation.clazz.ClazzView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Slf4j
public class NavigationController {
    @Getter
    private NavigationView view;
    private LoginModel model;

    public NavigationController(NavigationView view, LoginModel model) {
        this.view = view;
        this.model = model;

        // 按钮对象
        List<JButton> naviButtonList = view.getNaviButtonList();

        // 获取退出按钮对象 若要获取其他对象，请修改为其他ENUM值
        JButton exitButton = naviButtonList.get(NaviFunctionButtonEnum.EXIT.ordinal());
        // 添加监听
        exitButton.addActionListener(e -> System.exit(0));

        // Clazz层
        JButton clazzButton  = naviButtonList.get(NaviFunctionButtonEnum.CLAZZ.ordinal());
        clazzButton.addActionListener(e -> {
            List<UserEntity.Tabletime> tabletime = UserEntity.getUserInformation().getTabletime();
            // 模拟数据获取阻塞
            log.warn("阻塞模拟 for 1000ms...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            // 按图层顺序渲染，优先渲染导航栏。
            SwingUtilities.invokeLater(() -> {
                view.renderRouterView(new ClazzView(tabletime));
            });
        });

    }


    public String getName(){


        return "";
    }

}
