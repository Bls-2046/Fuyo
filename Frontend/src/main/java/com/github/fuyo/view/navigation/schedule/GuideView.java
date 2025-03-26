package com.github.fuyo.view.navigation.schedule;

import com.github.fuyo.utils.layout.RUILabel;
import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Data
public class GuideView extends JLayeredPane {

    @Getter
    private JTextField nickNameInput;

    @Getter
    private JButton submitButton;

    public GuideView() {

        // Fixed
        setBounds(260, 0, 1100, 768);

        RUILabel bg = new RUILabel("mainFrame/views/reminder", "GuideBg.png");
        add(bg.imageLabel(0, 0), DEFAULT_LAYER);

        nickNameInput = RUILabel.getEmptyInputTextLabel(244,229,613,45,
                "请输入微信号", Color.GRAY,20,"微软雅黑",Font.PLAIN);
        add(nickNameInput, POPUP_LAYER);

        submitButton = RUILabel.getStaticEmptyLayerButton(506,310,89,35);
        add(submitButton, POPUP_LAYER);

        setVisible(true);
    }

}
