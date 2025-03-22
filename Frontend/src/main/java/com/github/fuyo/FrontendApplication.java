package com.github.fuyo;

import com.github.fuyo.controller.LoginController;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.view.LoginView;

public class FrontendApplication {
    public static void main(String[] args) throws Exception {
        new LoginController(new LoginView(), new LoginModel()).getView().setVisible(true);
    }
}
