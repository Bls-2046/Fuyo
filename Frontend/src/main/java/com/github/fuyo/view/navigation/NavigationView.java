package com.github.fuyo.view.navigation;

import com.github.fuyo.entity.NaviFunctionEntity;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.layout.RUILabel;
import com.github.fuyo.view.navigation.index.HomeView;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NavigationView extends JFrame {

    private List<NaviFunctionEntity> naviFuncObjs = new ArrayList<NaviFunctionEntity>();

    @Getter
    private JLayeredPane viewLayerPanel;

    @Getter
    private JLayeredPane lp;

    // List for button
    @Getter
    List<JButton> naviButtonList = new ArrayList<>();

    private UserEntity userEntity = UserEntity.getUserInformation();

    public NavigationView() {
        initComponent();
    }

    private void initComponent() {
        log.info("initComponent, userEntity = " + userEntity);
        setTitle("Fuyo Navigator");
        setUndecorated(true);
        setResizable(false);
        setSize(1360, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setBackground(new Color(0, 0, 0, 0));

        // IME?
        setUndecorated(true);
        setBackground(new Color(0,0,0,1));
        setOpacity(0.99f);

        // JLayeredPane
        lp = getLayeredPane();

        // Get Current Time
        HomeView.Time time = HomeView.Time.MORNING;

        if ( LocalTime.now().isAfter(LocalTime.of(18,0)) ||
                LocalTime.now().isBefore(LocalTime.of(6,0)) ) {
            time = HomeView.Time.NIGHT;
        }

        // BaseFrame Implement
        RUILabel labelBaseFrame = new RUILabel("mainFrame",((time == HomeView.Time.MORNING) ? "baseFrameMorning.png" : "baseFrameNight.png"));
        lp.add(labelBaseFrame.imageLabel(0,0), JLayeredPane.DEFAULT_LAYER);

        RUILabel labelLogo = new RUILabel("loginFrame","logo.png");
        lp.add(labelLogo.imageLabel(25,35), JLayeredPane.PALETTE_LAYER);

        RUILabel avatarLine = new RUILabel("mainFrame","line.png");
        lp.add(avatarLine.imageLabel(28,700), JLayeredPane.PALETTE_LAYER);

        // AvatarDisp
        URL avatarIconFile = getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", "mainFrame/iconUserAvatar", userEntity.getUsername() + ".png"));
        RUILabel avatarImage = new RUILabel("mainFrame/iconUserAvatar", (avatarIconFile == null ? "default.png" : userEntity.getUsername() + ".png"));
        lp.add(avatarImage.imageLabel(28,717,37,37), JLayeredPane.PALETTE_LAYER);

        lp.add(RUILabel.getEmptyTextLabel(79,709,userEntity.getName(),20,"微软雅黑",Color.GRAY,Font.PLAIN), JLayeredPane.PALETTE_LAYER);

        // Function Navi Fixed

        NaviFunctionEntity navi0 = new NaviFunctionEntity("首页",false,lp,new int[]{17,101}, "index.png");
        naviFuncObjs.add(navi0);
        navi0.addToPanel();
        navi0.addActionListener(e -> actionPerformed(e,navi0));
        naviButtonList.add(navi0.getFunctionButton());

        NaviFunctionEntity navi1 = new NaviFunctionEntity("课程表",false,lp,new int[]{17,101 + 60}, "demo.png");
        naviFuncObjs.add(navi1);
        navi1.addToPanel();
        navi1.addActionListener(e -> actionPerformed(e,navi1));
        naviButtonList.add(navi1.getFunctionButton());

        NaviFunctionEntity navi2 = new NaviFunctionEntity("日期提醒",false,lp,new int[]{17,101 + 60 * 2}, "calendar.png");
        naviFuncObjs.add(navi2);
        navi2.addToPanel();
        navi2.addActionListener(e -> actionPerformed(e,navi2));
        naviButtonList.add(navi2.getFunctionButton());

        NaviFunctionEntity navi3 = new NaviFunctionEntity("DeepSeek",false,lp,new int[]{17,101 + 60 * 3}, "deepseek.png");
        naviFuncObjs.add(navi3);
        navi3.addToPanel();
        navi3.addActionListener(e -> actionPerformed(e,navi3));
        naviButtonList.add(navi3.getFunctionButton());

        // EXIT
        NaviFunctionEntity exitNavi = new NaviFunctionEntity("退出程序",false,lp,new int[]{17,101 + 60 * 9}, "exit.png");
        naviFuncObjs.add(exitNavi);
        exitNavi.addToPanel();
        naviButtonList.add(exitNavi.getFunctionButton());

        // UserPanel Single Handle
        JButton userButton = new JButton();
        userButton.setOpaque(false);
        userButton.setBackground(new Color(0,0,0,0));
        userButton.setBorder(BorderFactory.createEmptyBorder());
        userButton.setBounds(15, 707, 228, 54); // fixed
        userButton.setFocusPainted(false);
        userButton.setContentAreaFilled(false);
        userButton.addActionListener(e -> deActivateAll());
        naviButtonList.add(userButton);
        lp.add(userButton, JLayeredPane.POPUP_LAYER);

        // For debug usage, plz change this into controller layer
        // userButton.addActionListener(e -> {
        //     SwingUtilities.invokeLater(() -> {
        //         renderRouterView(new UserView(userEntity));
        //     });
        // });

        setVisible(true);
    }

    // Disable all navibar activate status when using userPanel
    private void deActivateAll() {

        naviFuncObjs.forEach(naviFuncObj -> {
            naviFuncObj.setDeActivate();
        });

        if (viewLayerPanel != null) {
            lp.remove(viewLayerPanel);
        }
        lp.revalidate();
        lp.repaint();

    }

    // Render NaviBar
    private void actionPerformed(ActionEvent e, NaviFunctionEntity navi) {
        log.info("Button {} Pressed and set to activate", navi.getFunctionName());

        // Initialization
        naviFuncObjs.forEach(naviFuncObj -> {
            naviFuncObj.setDeActivate();
        });

        if (viewLayerPanel != null) {
            lp.remove(viewLayerPanel);
        }
        lp.revalidate();
        lp.repaint();

        navi.setActivate();
    }

    // Render Router-View
    public void renderRouterView(JLayeredPane targetView) {
        viewLayerPanel = targetView;
        lp.add(viewLayerPanel, JLayeredPane.POPUP_LAYER);
    }
}
