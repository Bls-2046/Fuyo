package com.github.fuyo.view.messagebox;

import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.utils.layout.RUILabel;
import com.github.fuyo.view.navigation.schedule.ScheduleDialogView;
import com.github.fuyo.view.navigation.schedule.ScheduleView;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ErrorMessageBox extends JFrame {

    public ErrorMessageBox(String message) {

        setTitle("Fuyo Schedule Dialog");
        setUndecorated(true);
        setResizable(false);
        setSize(640, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        JLayeredPane lp = getLayeredPane();

        RUILabel bg = new RUILabel("messageBox","errorBg.png");
        lp.add(bg.imageLabel(0,0),JLayeredPane.DEFAULT_LAYER);

        lp.add(RUILabel.getCenterEmptyTextLabel(44,143,551,41,
                message
                , 27, "微软雅黑", Color.GRAY, Font.PLAIN), JLayeredPane.POPUP_LAYER
        );

        JButton closeButton = RUILabel.getStaticEmptyLayerButton(456,251,119,27);
        closeButton.addActionListener(e -> {
            dispose();
        });
        lp.add(closeButton, JLayeredPane.POPUP_LAYER);

        setVisible(true);
    }

    public static void showErrorBox(String message) {
        new ErrorMessageBox(message);
    }

}
