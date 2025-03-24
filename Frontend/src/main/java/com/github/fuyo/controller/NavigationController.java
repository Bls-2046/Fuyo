package com.github.fuyo.controller;

import com.github.fuyo.entity.NaviFunctionButtonEnum;
import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.Tabletime;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.listener.NavigationCloseListener;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.model.NavigationModel;
import com.github.fuyo.model.ScheduleModel;
import com.github.fuyo.model.UserViewModel;
import com.github.fuyo.view.LoginView;
import com.github.fuyo.view.navigation.NavigationView;
import com.github.fuyo.view.navigation.clazz.ClazzView;
import com.github.fuyo.view.navigation.schedule.ScheduleView;
import com.github.fuyo.view.navigation.user.UserView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NavigationController implements NavigationCloseListener {
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
            List<Tabletime> tabletime = user.getTabletime();

            // 按图层顺序渲染，优先渲染导航栏。
            SwingUtilities.invokeLater(() -> {
                view.renderRouterView(new ClazzView(tabletime));
            });
        });

        // 用户信息层
        JButton userButton = naviButtonList.get(NaviFunctionButtonEnum.USERLAYER.ordinal());
        userButton.addActionListener(e -> {

            UserViewController userViewController = new UserViewController(new UserView(user), new UserViewModel(), this);

            SwingUtilities.invokeLater(() -> {
                view.renderRouterView(userViewController.getView());
            });
        });

        // TODO: 当前为测试数据, 请在此处传入数据 List<ScheduleEntity>
        List<ScheduleEntity> schedules = new ArrayList<>();
        schedules.add(new ScheduleEntity("标题1", LocalDateTime.now(),null,"描述1......"));
        schedules.add(new ScheduleEntity("标题2", LocalDateTime.now(),null,"描述2......"));

        // Schedule层
        JButton scheduleButton = naviButtonList.get(NaviFunctionButtonEnum.NOTIFY.ordinal());
        scheduleButton.addActionListener(e -> {

            ScheduleController scheduleController = new ScheduleController(new ScheduleView(schedules), new ScheduleModel());

            SwingUtilities.invokeLater(() -> {
                view.renderRouterView(scheduleController.getView());
            });
        });

    }


    /**
     * 切换账号
     */
    @Override
    public void onClose() {
        closeView();
    }
    // 退出当前界面新建登录界面，清空当前用户数据和用户密码缓存
    public void closeView() {
        if (view != null) {
            SwingUtilities.invokeLater(() -> {
                view.dispose();
                try {
                    new LoginController(new LoginView(), new LoginModel()).getView().setVisible(true);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
        } else {
            log.error("NavigationView is null!");
        }
    }

}
