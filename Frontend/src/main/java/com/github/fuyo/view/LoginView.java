package com.github.fuyo.view;

import com.github.fuyo.entity.LoginViewEntity;
import com.github.fuyo.utils.layout.RUILabel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LoginView extends JFrame {

    private LoginViewEntity loginEntity;

    public LoginView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Fuyo Login");
        setUndecorated(true);
        setResizable(false);
        setSize(1002, 513);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        // JLayeredPane
        JLayeredPane lp = getLayeredPane();

        // 添加内容面板 (BaseFrame)
        RUILabel labelBaseFrame = new RUILabel("loginFrame", "baseFrame.png");
        lp.add(labelBaseFrame.imageLabel(0,0), JLayeredPane.DEFAULT_LAYER);

        // 添加图片预览组件
        RUILabel labelLeftImage = new RUILabel("loginFrame","leftImageDisp.png");
        lp.add(labelLeftImage.imageLabel(61,56), JLayeredPane.PALETTE_LAYER);

        // Logo
        RUILabel labelLogo = new RUILabel("loginFrame","logo.png");
        lp.add(labelLogo.imageLabel(643,161), JLayeredPane.PALETTE_LAYER);

        // JText
        RUILabel labelInputArea = new RUILabel("loginFrame","textInput.png");
        lp.add(labelInputArea.imageLabel(591,219), JLayeredPane.PALETTE_LAYER);
        lp.add(labelInputArea.imageLabel(591,219 + 50), JLayeredPane.PALETTE_LAYER);

        JTextField usernameInput = labelInputArea.getImageTextLabel(591,219,"",Color.GRAY,13,"微软雅黑");
        JPasswordField passwordInput = labelInputArea.passwordLabel(591,219 + 50,"",Color.GRAY,13,"微软雅黑");

        lp.add(usernameInput, JLayeredPane.PALETTE_LAYER);
        lp.add(passwordInput, JLayeredPane.PALETTE_LAYER);

        // Button
        RUILabel buttonLogin = new RUILabel("loginFrame","loginButton.png");
        RUILabel buttonExitProgram = new RUILabel("loginFrame","exitProgramButton.png");

        JButton loginButton = buttonLogin.buttonLabel(591,353);
        JButton exitProgramButton = buttonExitProgram.buttonLabel(591,393);

        lp.add(loginButton, JLayeredPane.PALETTE_LAYER);
        lp.add(exitProgramButton, JLayeredPane.PALETTE_LAYER);

        RUILabel errorFrame = new RUILabel("loginFrame","notifyFrameError.png");
        JLabel[] errorFrameDisp = errorFrame.errorLabel(550,40,"Test",Color.WHITE,14,"微软雅黑");
        lp.add(errorFrameDisp[0], JLayeredPane.PALETTE_LAYER);
        lp.add(errorFrameDisp[1], JLayeredPane.POPUP_LAYER);
        errorFrameDisp[0].setVisible(false);
        errorFrameDisp[1].setVisible(false);

        loginEntity = new LoginViewEntity(usernameInput, passwordInput, loginButton, exitProgramButton, errorFrameDisp);
    }

    public String getUsername() {
        return loginEntity.getUsernameObject().getText();
    }

    public String getPassword() {
        return new String(loginEntity.getPasswordObject().getPassword());
    }

    public JButton getLoginButton() {
        return loginEntity.getLoginButtonObject();
    }

    public JButton getExitProgramButton() {
        return loginEntity.getExitProgramButtonObject();
    }

    // 错误消息提示
    public void showErrorFrame(String message) {
        // 替换文本有问题
        JLabel errorFrameTarget = loginEntity.getErrorFrameDisp()[0];
        JLabel messageTarget = loginEntity.getErrorFrameDisp()[1];
        messageTarget.setText(message);
        errorFrameTarget.setVisible(true);
        messageTarget.setVisible(true);
    }

    public JTextField getUsernameInput() {
        return loginEntity.getUsernameObject();
    }

    public JPasswordField getPasswordInput() {
        return loginEntity.getPasswordObject();
    }

    public void clearInputs() {
        loginEntity.getUsernameObject().setText("");
        loginEntity.getPasswordObject().setText("");
    }

    public void setLoginButtonEnabled(boolean statusButton) {
        loginEntity.getLoginButtonObject().setEnabled(statusButton);
    }

    public void setInputEnabled(boolean statusInput) {
        loginEntity.getUsernameObject().setEditable(statusInput);
        loginEntity.getPasswordObject().setEditable(statusInput);
    }
}