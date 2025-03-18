package com.github.fuyo.controller;

import com.github.fuyo.model.LoginModel;
import com.github.fuyo.view.LoginView;
import com.github.fuyo.view.NavigationView;
import lombok.Getter;

import javax.swing.*;
import java.util.Objects;

public class LoginController {
    @Getter
    private final LoginView view;
    private final LoginModel model;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        // 监听器
        view.getLoginButton().addActionListener(e -> ManualLogin());
        view.getExitProgramButton().addActionListener(e -> System.exit(0));

        AutoLogin(); // 尝试自动登录
    }

    /**
     * 自动登录
     */
    private void AutoLogin() {
        // 判断登录缓存文件是否存在
        try {
            if (model.isExistLoginFile()) {
                // 若登录数据文件存在并且不为空, 读文件获取用户名和密码
                String[] loginData = model.readCredentials();
                String username = loginData[0];
                String password = loginData[1];

                // 将用户名自动填入输入框
                view.getUsernameInput().setText(username);
                view.getPasswordInput().setText(password);

                view.setInputEnabled(false); // 锁输入框
                view.setLoginButtonEnabled(false); // 锁登录按钮

                isSuccessfulLogin(username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorFrame("登录异常");
            view.clearInputs();
        } finally {
            view.setInputEnabled(true);
            view.setLoginButtonEnabled(true);
        }
    }

    /**
     * 手动登录
     */
    private void ManualLogin() {
        try {
            // 获取用户输入的用户名和密码
            String username = view.getUsername();
            String password = view.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                view.showErrorFrame("用户名或密码不能为空");
                view.clearInputs();
            } else {
                isSuccessfulLogin(username, password);
            }
        } catch (Exception e) {
            view.showErrorFrame("登录异常");
            view.clearInputs();
        } finally {
            view.setInputEnabled(true);
            view.setLoginButtonEnabled(true);
        }
    }

    private void isSuccessfulLogin(String username, String password) {
        String message = model.LoginVerification(username, password);
        // 验证
        if (Objects.equals(message, "登录成功")) {
            SwingUtilities.invokeLater(() -> {
                System.out.println("登录成功");
                view.dispose();
                new NavigationController(new NavigationView(), new LoginModel()).getView().setVisible(true);
            });
        } else {
            view.showErrorFrame(message);
            view.clearInputs();
        }
    }
}
