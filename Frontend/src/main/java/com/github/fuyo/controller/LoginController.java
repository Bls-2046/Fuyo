package com.github.fuyo.controller;

import com.github.fuyo.model.layout.LoadingModel;
import com.github.fuyo.model.LoginModel;
import com.github.fuyo.view.LoginView;
import com.github.fuyo.view.load.LoadingView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.Objects;

@Slf4j
public class LoginController {
    @Getter
    private final LoginView view;
    private final LoginModel model;

    public LoginController(LoginView view, LoginModel model) throws Exception {
        this.view = view;
        this.model = model;

        // 监听器
        view.getLoginButton().addActionListener(e -> {
            try {
                ManualLogin();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }); // 手动登录

        view.getExitProgramButton().addActionListener(e -> System.exit(0)); // 退出程序

        AutoLogin(); // 尝试自动登录
    }

    /**
     * 自动登录
     */
    private void AutoLogin() throws Exception {
        // 判断登录缓存文件是否存在
        try {
            if (model.isExistLoginFile()) {

                view.setInputEnabled(false); // 锁输入框
                view.setLoginButtonEnabled(false); // 锁登录按钮

                // 若登录数据文件存在并且不为空, 读文件获取用户名和密码
                String[] loginData = model.readCredentials();
                String username = loginData[0];
                String password = loginData[1];

                // 将用户名自动填入输入框
                view.getUsernameInput().setText(username);
                view.getPasswordInput().setText(password);

                isSuccessfulLogin(username, password);
            }
        } catch (Exception e) {
            log.error(e.getMessage());

            view.showErrorFrame("登录异常, 请稍后重试");
            view.clearInputs();
            view.setInputEnabled(true);
            view.setLoginButtonEnabled(true);

            model.clearFileContent();
        }
    }

    /**
     * 手动登录
     */
    private void ManualLogin() throws Exception {
        try {
            view.setInputEnabled(false); // 锁输入框
            view.setLoginButtonEnabled(false); // 锁登录按钮

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
            log.error(e.getMessage());

            view.showErrorFrame("登录异常, 请稍后重试");
            view.clearInputs();
            view.setInputEnabled(true);
            view.setLoginButtonEnabled(true);

            model.clearFileContent();
        }
    }

    private void isSuccessfulLogin(String username, String password) throws Exception {
        String message = model.loginVerification(username, password);
        // 验证
        if (Objects.equals(message, "登录成功")) {
            model.saveCredentials(username, password);

            // 使用 Timer 延迟 5 秒后关闭窗口
            Timer timer = new Timer(1000, e -> {
                view.dispose(); // 关闭当前窗口

                // 加载界面

                // 创建 NavigationController 实例
                // new NavigationController(new NavigationView(), new NavigationModel()).getView().setVisible(true);

                // 创建加载窗口(LoadingController)实例
                new LoadingController(new LoadingView(), new LoadingModel()).getView().setVisible(true);
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            view.showErrorFrame(message);
            view.clearInputs();
            view.setInputEnabled(true);
            view.setLoginButtonEnabled(true);
        }
    }
}
