package com.github.fuyo.controller;

import com.github.fuyo.model.HomeModel;
import com.github.fuyo.view.navigation.home.HomeView;
import lombok.Getter;

public class HomeController {
    @Getter
    private HomeView view;
    private HomeModel model;

    public HomeController(HomeView view, HomeModel model) {
        this.view = view;
        this.model = model;
    }
}
