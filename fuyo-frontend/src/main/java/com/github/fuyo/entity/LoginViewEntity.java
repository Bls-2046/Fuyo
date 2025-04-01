package com.github.fuyo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginViewEntity {
    private JTextField usernameObject;
    private JPasswordField passwordObject;
    private JButton loginButtonObject;
    private JButton exitProgramButtonObject;
    private JLabel[] errorFrameDisp;
}
