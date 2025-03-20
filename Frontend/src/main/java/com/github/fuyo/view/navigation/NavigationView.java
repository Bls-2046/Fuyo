package com.github.fuyo.view.navigation;

import com.github.fuyo.entity.NaviFunctionEntity;
import com.github.fuyo.service.NaviServices;
import com.github.fuyo.service.impl.NaviServicesImpl;
import com.github.fuyo.utils.layout.RUILabel;
import com.github.fuyo.view.navigation.clazz.ClazzView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NavigationView extends JFrame {

    private List<NaviFunctionEntity> naviFuncObjs = new ArrayList<NaviFunctionEntity>();
    private JLayeredPane viewLayerPanel;
    private JLayeredPane lp;

    // Services
    private NaviServices naviServices = new NaviServicesImpl();

    @Setter
    private int userId = 1;

    public NavigationView() {

        setTitle("REACTION NETWORK UI DEMO");
        setUndecorated(true);
        setResizable(false);
        setSize(1360, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        // JLayeredPane
        lp = getLayeredPane();

        // BaseFrame Implement
        RUILabel labelBaseFrame = new RUILabel("mainFrame","baseFrame.png");
        lp.add(labelBaseFrame.imageLabel(0,0), JLayeredPane.DEFAULT_LAYER);

        RUILabel labelLogo = new RUILabel("loginFrame","logo.png");
        lp.add(labelLogo.imageLabel(25,35), JLayeredPane.PALETTE_LAYER);

        RUILabel avatarLine = new RUILabel("mainFrame","line.png");
        lp.add(avatarLine.imageLabel(28,700), JLayeredPane.PALETTE_LAYER);

        RUILabel avatarImage = new RUILabel("mainFrame/iconUserAvatar","default.png");
        lp.add(avatarImage.imageLabel(28,717), JLayeredPane.PALETTE_LAYER);

        lp.add(RUILabel.getTextLabel(79,709,"Username",20,"微软雅黑",Color.GRAY,Font.PLAIN), JLayeredPane.PALETTE_LAYER);

        // Function Navi Fixed
        NaviFunctionEntity navi1 = new NaviFunctionEntity("课程表",true,lp,new int[]{17,101}, "demo.png");
        naviFuncObjs.add(navi1);
        navi1.addToPanel();
        navi1.addActionListener(e -> actionPerformed(e,navi1));
        viewLayerPanel = new ClazzView(naviServices.getUserInformation(userId));
        lp.add(viewLayerPanel, JLayeredPane.PALETTE_LAYER);

        NaviFunctionEntity navi2 = new NaviFunctionEntity("日期提醒",false,lp,new int[]{17,101 + 60}, "calendar.png");
        naviFuncObjs.add(navi2);
        navi2.addToPanel();
        navi2.addActionListener(e -> actionPerformed(e,navi2));

        NaviFunctionEntity navi3 = new NaviFunctionEntity("DeepSeek",false,lp,new int[]{17,101 + 60 * 2}, "deepseek.png");
        naviFuncObjs.add(navi3);
        navi3.addToPanel();
        navi3.addActionListener(e -> actionPerformed(e,navi3));

        // EXIT
        NaviFunctionEntity exitNavi = new NaviFunctionEntity("退出程序",false,lp,new int[]{17,101 + 60 * 9}, "exit.png");
        naviFuncObjs.add(exitNavi);
        exitNavi.addToPanel();
        exitNavi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Function like Vue-RouterView
    private void actionPerformed(ActionEvent e, NaviFunctionEntity navi) {
        log.info("Button {} Pressed and set to activate", navi.getFunctionName());

        // Initialization
        naviFuncObjs.forEach(naviFuncObj -> {
            naviFuncObj.setDeActivate();
        });
        if (viewLayerPanel != null) {
            lp.remove(viewLayerPanel);
        }
        viewLayerPanel = null;
        navi.setActivate();
        lp.revalidate();
        lp.repaint();

        // Create Panel
        if (navi.getFunctionName().equals("课程表")) {
            viewLayerPanel = new ClazzView(naviServices.getUserInformation(userId));
            lp.add(viewLayerPanel, JLayeredPane.PALETTE_LAYER);
        }
    }

}
