package com.github.fuyo;

import com.github.fuyo.controller.LoginController;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.view.LoginView;

public class FrontendApplication {
    public static void main(String[] args) throws Exception {

        // IME Fix
        System.setProperty("sun.java2d.noddraw", "true");

        new LoginController(new LoginView(), new LoginModel()).getView().setVisible(true);
    }
}
