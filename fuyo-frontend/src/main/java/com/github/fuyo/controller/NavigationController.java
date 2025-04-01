package com.github.fuyo.controller;

import com.github.fuyo.entity.*;
import com.github.fuyo.listener.NavigationCloseListener;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.model.WeChatModel;
import com.github.fuyo.model.layout.NavigationModel;
import com.github.fuyo.model.ScheduleModel;
import com.github.fuyo.model.UserViewModel;
import com.github.fuyo.view.LoginView;
import com.github.fuyo.view.messagebox.ErrorMessageBox;
import com.github.fuyo.view.navigation.NavigationView;
import com.github.fuyo.view.navigation.clazz.ClazzView;
import com.github.fuyo.view.navigation.deepseek.WebRenderView;
import com.github.fuyo.view.navigation.index.HomeView;
import com.github.fuyo.view.navigation.schedule.GuideView;
import com.github.fuyo.view.navigation.schedule.ScheduleView;
import com.github.fuyo.view.navigation.user.UserView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
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

        // 首次渲染首页
        SwingUtilities.invokeLater(() -> {
            view.renderRouterView(new HomeView());
        });

        // Index层
        JButton indexButton  = naviButtonList.get(NaviFunctionButtonEnum.INDEX.ordinal());
        indexButton.addActionListener(e -> {
            // 按图层顺序渲染，优先渲染导航栏。
            SwingUtilities.invokeLater(() -> {
                view.renderRouterView(new HomeView());
            });
        });

        // Clazz层
        JButton clazzButton  = naviButtonList.get(NaviFunctionButtonEnum.CLAZZ.ordinal());
        clazzButton.addActionListener(e -> {
            List<TabletimeEntity> tabletimeEntity = user.getTabletimeEntity();

            // 按图层顺序渲染，优先渲染导航栏。
            SwingUtilities.invokeLater(() -> {
                view.renderRouterView(new ClazzView(tabletimeEntity));
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

        // Schedule层
        JButton scheduleButton = naviButtonList.get(NaviFunctionButtonEnum.NOTIFY.ordinal());
        scheduleButton.addActionListener(e -> {

            List<ScheduleEntity> scheduleEntities;

            if (user.getSchedule() != null) {
                scheduleEntities = user.getSchedule();
            } else {
                log.warn("user.getSchedule() is null!!!");
                scheduleEntities = new ArrayList<>();
            }

            // TODO
            if (user.getWechatUser().getNickname() == null) {
                log.warn("user.getWechatUser() is null, goto GuideView");

                SwingUtilities.invokeLater(() -> {
                    // 没有获取到微信用户数据
                    GuideView guideView = new GuideView();
                    SwingUtilities.invokeLater(() -> {
                       view.renderRouterView(guideView);
                    });
                    JButton submitButton = guideView.getSubmitButton();
                    submitButton.addActionListener(e2 -> {

                        // 验证用户微信昵称
                        boolean isVaild = false;

                        WeChatEntity wechatUser =  UserEntity.getUserInformation().getWechatUser();
                        System.out.println(wechatUser);

                        String nickname = wechatUser.getNickname();
                        if (nickname == null) {
                            nickname = guideView.getNickNameInput().getText();
                            Boolean updateWeChatNickNameResult = WeChatModel.updateWeChatNickName(nickname);
                            System.out.println("updateWeChatNicknameResult: " + updateWeChatNickNameResult);
                            if (updateWeChatNickNameResult) {
                                UserEntity.getUserInformation().getWechatUser().setNickname(nickname);
                                isVaild = true;
                            } else {
                                guideView.getNickNameInput().setText("");
                            }
                        }

                        if (isVaild) {
                            view.getLp().remove(guideView);
                            view.revalidate();
                            view.repaint();
                            ScheduleController scheduleController = new ScheduleController(new ScheduleView(scheduleEntities), new ScheduleModel());

                            SwingUtilities.invokeLater(() -> {
                                view.renderRouterView(scheduleController.getView());
                            });
                        } else {
                            ErrorMessageBox.showErrorBox("找不到该用户");
                        }

                    });
                });

            } else {

                ScheduleController scheduleController = new ScheduleController(new ScheduleView(scheduleEntities), new ScheduleModel());

                SwingUtilities.invokeLater(() -> {
                    view.renderRouterView(scheduleController.getView());
                });
            }
        });

        // DeepSeek
        JButton deepseekButton = naviButtonList.get(NaviFunctionButtonEnum.DEEPSEEK.ordinal());
        deepseekButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {

                WebRenderView deepseekView = new WebRenderView();

                // URL
                SwingUtilities.invokeLater(() -> {
                    view.renderRouterView(deepseekView);
                    deepseekView.loadURL("http://localhost/");
                });

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
