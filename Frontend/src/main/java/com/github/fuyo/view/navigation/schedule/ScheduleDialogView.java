package com.github.fuyo.view.navigation.schedule;

import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.utils.layout.RUILabel;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ScheduleDialogView extends JFrame {

    public ScheduleDialogView(ScheduleEntity scheduleEntity) {

        setTitle("Fuyo Schedule Dialog");
        setUndecorated(true);
        setResizable(false);
        setSize(640, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        JLayeredPane lp = getLayeredPane();

        RUILabel bg = new RUILabel("mainFrame/views/reminder","scheduleInfo.png");
        lp.add(bg.imageLabel(0,0),JLayeredPane.DEFAULT_LAYER);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = scheduleEntity.getDatetime().format(dtf);

        lp.add(RUILabel.getCenterEmptyTextLabel(44,116,551,22,
                time
                , 15, "微软雅黑", Color.GRAY, Font.PLAIN), JLayeredPane.POPUP_LAYER
        );

        lp.add(RUILabel.getCenterEmptyTextLabel(44,143,551,41,
                scheduleEntity.getTitle()
                , 27, "微软雅黑", Color.GRAY, Font.PLAIN), JLayeredPane.POPUP_LAYER
        );

        lp.add(RUILabel.getCenterEmptyTextLabel(44,191,551,41,
                ScheduleView.wordLimitation(scheduleEntity.getDescription())
                , 22, "微软雅黑", Color.GRAY, Font.PLAIN), JLayeredPane.POPUP_LAYER
        );

        JButton closeButton = RUILabel.getStaticEmptyLayerButton(456,251,119,27);
        closeButton.addActionListener(e -> {
            dispose();
        });
        lp.add(closeButton, JLayeredPane.POPUP_LAYER);

        setVisible(true);
    }

    public static void showDialog(ScheduleEntity scheduleEntity) {
        new ScheduleDialogView(scheduleEntity);
    }

}
