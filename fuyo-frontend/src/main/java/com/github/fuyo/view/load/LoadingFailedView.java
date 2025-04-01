package com.github.fuyo.view.load;

import com.github.fuyo.utils.layout.RUILabel;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Data
public class LoadingFailedView extends JFrame {

    @Getter
    private JButton retryButton;

    @Getter
    private JButton exitButton;

    public LoadingFailedView() { initComponents(); }

    private void initComponents() {

        setTitle("Fuyo LoadError");
        setUndecorated(true);
        setResizable(false);
        setSize(587, 295);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        JLayeredPane lp = getLayeredPane();
        RUILabel bgLayer = new RUILabel("loadingFrame","loadingFailed.png");
        lp.add(bgLayer.imageLabel(0,0),JLayeredPane.DEFAULT_LAYER);

        retryButton = RUILabel.getStaticEmptyLayerButton(300,238,117,26);
        lp.add(retryButton,JLayeredPane.POPUP_LAYER);

        exitButton = RUILabel.getStaticEmptyLayerButton(430,238,117,26);
        lp.add(exitButton,JLayeredPane.POPUP_LAYER);

        setVisible(true);
    }

}
