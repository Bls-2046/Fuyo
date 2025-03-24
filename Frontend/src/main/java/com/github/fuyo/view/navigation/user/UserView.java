package com.github.fuyo.view.navigation.user;

import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.layout.RUILabel;
import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalTime;

@Data
public class UserView extends JLayeredPane {

    private UserEntity user;
    private final int offset_x = 15;
    private final int offset_y = 10;

    @Getter
    private JButton switchAccountButton = new JButton();

    public UserView(UserEntity user) {

        // Layer Implement
        this.user = user;
        setBounds(260, 0, 1100, 768);

        RUILabel bgLabel = new RUILabel("mainFrame/views/user", "BaseLayer.png");
        add(bgLabel.imageLabel(69 - offset_x,59 - offset_y),DEFAULT_LAYER);

        // Avatar information Implement
        // Time
        String message = "";
        if (LocalTime.now().isAfter(LocalTime.of(18,0))) message = "晚上好";
        else if (LocalTime.now().isAfter(LocalTime.of(14,0))) message = "下午好";
        else if (LocalTime.now().isAfter(LocalTime.of(12,0))) message = "中午好";
        else if (LocalTime.now().isAfter(LocalTime.of(6, 0))) message = "早上好";
        else if (LocalTime.now().isAfter(LocalTime.of(0,0))) message = "夜深啦";

        // Layer
        URL avatarIconFile = getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", "mainFrame/iconUserAvatar", user.getUsername() + ".png"));
        RUILabel avatarImage = new RUILabel("mainFrame/iconUserAvatar", (avatarIconFile == null ? "default.png" : user.getUsername() + ".png"));
        add(avatarImage.imageLabel(125 - offset_x,97), PALETTE_LAYER);
        add(RUILabel.getEmptyTextLabel(383+10,137 + 7, message,40,"微软雅黑", Color.GRAY,Font.BOLD),POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(383+160,137 + 5, user.getName() + " ~",48,"微软雅黑", Color.GRAY,Font.BOLD),POPUP_LAYER);

        // Information Panel
        add(RUILabel.getEmptyTextLabel(607,398-12, user.getDepartment(),22,"微软雅黑", Color.GRAY,Font.PLAIN),POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(607,483-12, user.getPhone(),22,"微软雅黑", Color.GRAY,Font.PLAIN),POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(607,571-12, user.getUsername(),22,"微软雅黑", Color.GRAY,Font.PLAIN),POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(607,660-12, user.getEmail(),22,"微软雅黑", Color.GRAY,Font.PLAIN),POPUP_LAYER);

        // Button Layer
        switchAccountButton = RUILabel.getStaticEmptyLayerButton(205-offset_x, 591-offset_y, 413, 284);
        add(switchAccountButton,POPUP_LAYER);

    }

}
