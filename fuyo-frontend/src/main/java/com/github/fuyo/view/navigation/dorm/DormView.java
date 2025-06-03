package com.github.fuyo.view.navigation.dorm;

import com.github.fuyo.entity.DormitoryEntity;
import com.github.fuyo.utils.layout.RUILabel;

import javax.swing.*;
import java.awt.*;

public class DormView extends JLayeredPane {

    private DormitoryEntity dormitory;

    public DormView(DormitoryEntity dormitory) {
        this.dormitory = dormitory;

        setBounds(260, 0, 1100, 768);

        RUILabel bgLabel = new RUILabel("mainFrame/views/dorm", "bg.png");
        add(bgLabel.imageLabel(0, 0), DEFAULT_LAYER);

        JLabel dormNo = RUILabel.getEmptyTextLabel(367,257,dormitory.getDormNo(), 40, "微软雅黑", Color.DARK_GRAY);
        add(dormNo, POPUP_LAYER);

        JLabel waterFee = RUILabel.getEmptyTextLabel(270,481,String.valueOf(dormitory.getWaterFee()), 40, "微软雅黑", Color.DARK_GRAY);
        add(waterFee, POPUP_LAYER);

        JLabel electFee = RUILabel.getEmptyTextLabel(703,481,String.valueOf(dormitory.getElectricityFee()), 40, "微软雅黑", Color.DARK_GRAY);
        add(electFee, POPUP_LAYER);

        setVisible(true);
    }

}
