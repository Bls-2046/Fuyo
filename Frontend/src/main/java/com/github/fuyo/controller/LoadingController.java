package com.github.fuyo.controller;

import com.github.fuyo.model.LoadingFailedModel;
import com.github.fuyo.model.LoadingModel;
import com.github.fuyo.model.NavigationModel;
import com.github.fuyo.view.load.LoadingFailedView;
import com.github.fuyo.view.load.LoadingView;
import com.github.fuyo.view.navigation.NavigationView;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public class LoadingController {

    @Getter
    private final LoadingView view;
    private final LoadingModel model;

    public LoadingController(LoadingView view, LoadingModel model) {

        this.view = view;
        this.model = model;

        // 多线程Handle
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // async() => {};
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            // 这里改成实际逻辑, debug窗口输入true/false模拟线程返回结果正确与否
            log.warn("Warning: Plz enter test boolean return val -> ");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            // 这里返回true/false
            return Boolean.parseBoolean(input);
        }, executor);

        // const result = await newThreadMission();
        future.thenAccept(result -> {

            if (result){ // 成功
                view.renderSuccess();
                // Navi窗口层
                Timer timer = new Timer(3000, e -> {
                    view.dispose(); // 关闭当前窗口
                    // 创建 NavigationController 实例
                    new NavigationController(new NavigationView(), new NavigationModel()).getView().setVisible(true);
                    executor.shutdown();
                });
                timer.setRepeats(false);
                timer.start();

            } else {

                // 错误窗口层
                Timer timer = new Timer(200, e -> {
                    view.dispose(); // 关闭当前窗口
                    new LoadingFailedController(new LoadingFailedView(), new LoadingFailedModel()).getView().setVisible(true);
                    executor.shutdown();
                });
                timer.setRepeats(false);
                timer.start();

            }

        });

    }

}
