package com.github.fuyo.view.load;

import com.github.fuyo.utils.layout.RUILabel;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class LoadingView extends JFrame {

    private JLayeredPane lp;
    private JLayeredPane progressBarLayer;
    private RUILabel progressRUILabel;

    public LoadingView() {
        initComponent();
    }

    private void initComponent() {

        setTitle("Fuyo Loading");
        setUndecorated(true);
        setResizable(false);
        setSize(587, 295);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        lp = getLayeredPane();

        RUILabel bgLayer = new RUILabel("loadingFrame","loadingFrame.png");
        lp.add(bgLayer.imageLabel(0,0),JLayeredPane.DEFAULT_LAYER);
        setVisible(true);

        // ProgressBar
        progressRUILabel = new RUILabel("loadingFrame","progressBar.png");
        // Load
        lp.add(progressRUILabel.imageLabel(163,214,150,4),JLayeredPane.POPUP_LAYER);

    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void renderSuccess(){
        // Success, render SuccessLabel
        SwingUtilities.invokeLater(() -> {
            lp.add(progressRUILabel.imageLabel(163,214,259,4),JLayeredPane.POPUP_LAYER);
        });
    }

}
