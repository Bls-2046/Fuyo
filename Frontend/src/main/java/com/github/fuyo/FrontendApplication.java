package com.github.fuyo;

import com.github.fuyo.controller.LoginController;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.view.LoginView;
import com.github.fuyo.view.navigation.NavigationView;

public class FrontendApplication {
    public static void main(String[] args) throws Exception {
        new LoginController(new LoginView(), new LoginModel()).getView().setVisible(true);
        //new NavigationView().setVisible(true);
    }
}
