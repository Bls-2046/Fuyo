package com.github.fuyo.view.navigation.schedule;

import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.utils.layout.RUILabel;

import javax.swing.*;
import java.awt.*;

public class ScheduleDialogView extends JFrame {

    public ScheduleDialogView(ScheduleEntity scheduleEntity) {

        setTitle("Fuyo Schedule Dialog");
        setUndecorated(true);
        setResizable(false);
        setSize(640, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        JLayeredPane lp = getLayeredPane();

        RUILabel bg = new RUILabel("mainFrame/views/reminder","scheduleInfo.png");
        lp.add(bg.imageLabel(0,0),JLayeredPane.DEFAULT_LAYER);

        // lp.add(RUILabel.getEmptyTextLabel())

    }

}
