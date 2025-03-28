package com.github.fuyo.controller;

import com.github.fuyo.model.layout.LoadingFailedModel;
import com.github.fuyo.view.load.LoadingFailedView;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
@Data
public class LoadingFailedController {

    private LoadingFailedView view;
    private LoadingFailedModel model;

    public LoadingFailedController(LoadingFailedView view, LoadingFailedModel model) {
        this.view = view;
        this.model = model;

        JButton exitButton = view.getExitButton();
        JButton retryButton = view.getRetryButton();

        exitButton.addActionListener(e -> {
            log.info("Exit button clicked");
            System.exit(0);
        });

        retryButton.addActionListener(e -> {
            log.info("Retry button clicked");
        });
    }

}
