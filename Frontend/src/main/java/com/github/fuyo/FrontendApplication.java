package com.github.fuyo;

import com.github.fuyo.controller.LoginController;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.view.LoginView;
import com.github.fuyo.view.navigation.NavigationView;

public class FrontendApplication {
    public static void main(String[] args) {
        // TODO: 请在登陆成功后传入userId参数以获取用户数据。
        new LoginController(new LoginView(), new LoginModel()).getView().setVisible(true);
        //new NavigationView().setVisible(true);
    }
}
