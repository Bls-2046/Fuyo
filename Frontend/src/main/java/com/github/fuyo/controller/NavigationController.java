package com.github.fuyo.controller;

import com.github.fuyo.model.LoginModel;
import com.github.fuyo.view.navigation.NavigationView;
import lombok.Getter;

public class NavigationController {
    @Getter
    private NavigationView view;
    private LoginModel model;

    public NavigationController(NavigationView view, LoginModel model) {
        this.view = view;
        this.model = model;
    }

    public String getName(){


        return "";
    }
}
