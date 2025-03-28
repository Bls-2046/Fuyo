package com.github.fuyo.controller;

import com.github.fuyo.model.layout.LoadingFailedModel;
import com.github.fuyo.model.layout.LoadingModel;
import com.github.fuyo.model.layout.NavigationModel;
import com.github.fuyo.view.load.LoadingFailedView;
import com.github.fuyo.view.load.LoadingView;
import com.github.fuyo.view.navigation.NavigationView;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Data
public class LoadingController {

    private final LoadingView view;
    private final LoadingModel model;

    public LoadingController(LoadingView view, LoadingModel model) {

        this.view = view;
        this.model = model;

        // 多线程Handle
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // async() => {};
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {

            Boolean checkResult = model.checkDataIntegrity();

            // 可有可无
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3001));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            return checkResult;
        }, executor);

        // const result = await newThreadMission();
        future.thenAccept(result -> {

            if (result){ // 成功
                view.renderSuccess();
                // Navi窗口层
                Timer timer = new Timer(750, e -> {
                    view.dispose(); // 关闭当前窗口
                    // 创建 NavigationController 实例
                    new NavigationController(new NavigationView(), new NavigationModel()).getView().setVisible(true);
                    executor.shutdown();
                });
                timer.setRepeats(false);
                timer.start();

            } else {

                // 错误窗口层
                Timer timer = new Timer(1000, e -> {
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
