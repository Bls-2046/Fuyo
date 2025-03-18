package com.github.fuyo.utils.layout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.util.Objects;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RUILabel {

    private String fileType;
    private String fileName;

    /**
     * 创建图片图层
     * @param posX 坐标X
     * @param posY 坐标Y
     * @return 图片图层
     */
    public JLabel imageLabel(int posX, int posY) {

        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType, fileName)))
        );

        JLabel label = new JLabel(icon);
        label.setOpaque(false);
        label.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());

        return label;
    };

    public ImageIcon getImageIcon() {
        return new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType, fileName)))
        );
    }

    /**
     * 对象方式获取带图片的TextLabel
     * @param posX 位置X
     * @param posY 位置Y
     * @param message 显示文字
     * @param color 颜色
     * @param fontSize 字体大小
     * @param fontFamily 字体家族
     * @return 对象
     */
    public JTextField getTextLabel(int posX, int posY, String message, Color color, int fontSize, String fontFamily) {
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType, fileName)))
        );

        JTextField textField = new JTextField();
        textField.setText(message);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font(fontFamily, Font.BOLD, fontSize));
        textField.setForeground(color);

        return textField;
    }

    /**
     * 对象方式获取带图片的PasswordLabel
     * @param posX 位置X
     * @param posY 位置Y
     * @param message 显示文字
     * @param color 颜色
     * @param fontSize 字体大小
     * @param fontFamily 字体家族
     * @return 对象
     */
    public JPasswordField passwordLabel(int posX, int posY, String message, Color color, int fontSize, String fontFamily) {
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType, fileName)))
        );

        JPasswordField passwordField = new JPasswordField();
        passwordField.setText(message);
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setFont(new Font(fontFamily, Font.BOLD, fontSize));
        passwordField.setForeground(color);

        return passwordField;
    }

    /**
     * 对象方式获取带图片的ButtonLabel
     * @param posX 位置X
     * @param posY 位置Y
     * @return 对象
     */
    public JButton buttonLabel(int posX, int posY) {

        // Button ICON UnPressed
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType, fileName)))
        );

        // Button ICON Pressed
        String pressedFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String pressedFileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        // log.info("ResultNameCheck @ RUI.ButtonLabel: {} ", pressedFileName + "Pressed." + pressedFileType);
        ImageIcon iconPressed = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType,
                        pressedFileName + "Pressed." + pressedFileType)))
        );
        JButton button = new JButton();

        button.setIcon(icon);
        button.setOpaque(false);
        button.setBackground(new Color(0,0,0,0));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setPressedIcon(iconPressed);
        button.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    /**
     * 对象方式获取带图片&事件触发器的ButtonLabel
     * @param posX 位置X
     * @param posY 位置Y
     * @param action 事件触发器
     * @return 对象
     */
    public JButton buttonLabel(int posX, int posY, ActionListener action) {

        // Button ICON UnPressed
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType, fileName)))
        );

        // Button ICON Pressed
        String pressedFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String pressedFileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        log.info("ResultNameCheck @ RUI.ButtonLabel: {} ", pressedFileName + "Pressed." + pressedFileType);
        ImageIcon iconPressed = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType,
                        pressedFileName + "Pressed." + pressedFileType)))
        );
        JButton button = new JButton();

        button.setIcon(icon);
        button.setOpaque(false);
        button.setBackground(new Color(0,0,0,0));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setPressedIcon(iconPressed);
        button.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(e -> action.actionPerformed(e));

        return button;
    }

    /**
     * 对象方式获取带图片的errorLabel 错误提示框
     * @param posX 位置X
     * @param posY 位置Y
     * @param message 显示文字
     * @param color 颜色
     * @param fontSize 字体大小
     * @param fontFamily 字体家族
     * @return 对象
     */
    public JLabel[] errorLabel(int posX, int posY, String message, Color color, int fontSize, String fontFamily) {
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("staticImage/%s/%s", fileType, fileName)))
        );

        JLabel label = new JLabel(icon);
        label.setOpaque(false);
        label.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());
        label.setBackground(new Color(0,0,0,0));
        label.setBorder(BorderFactory.createEmptyBorder());

        JLabel text = new JLabel();
        text.setBounds(posX, posY-4, icon.getIconWidth(), icon.getIconHeight()); // Y-2 = OFFSET FIX
        text.setForeground(color);
        text.setFont(new Font(fontFamily, Font.BOLD, fontSize));
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setText(message);

        return new JLabel[]{label, text};
    }

    /**
     * 静态方式获取文字Label
     * @param posX 位置X
     * @param posY 位置Y
     * @param message 显示文字
     * @param color 颜色
     * @param fontSize 字体大小
     * @param fontFamily 字体家族
     * @return 对象
     */
    public static JLabel getTextLabel(int posX, int posY, String message, int fontSize, String fontFamily, Color color) {
        JLabel text = new JLabel();
        text.setBounds(posX, posY, 1000, 50);
        text.setForeground(color);
        text.setFont(new Font(fontFamily, Font.BOLD, fontSize));
        text.setHorizontalAlignment(JLabel.LEFT);
        text.setText(message);

        return text;
    }

    /**
     * 静态方式获取文字Label
     * @param posX 位置X
     * @param posY 位置Y
     * @param message 显示文字
     * @param color 颜色
     * @param fontSize 字体大小
     * @param fontFamily 字体家族
     * @param fontStyle 字体样式
     * @return 对象
     */
    public static JLabel getTextLabel(int posX, int posY, String message, int fontSize, String fontFamily, Color color, int fontStyle) {
        JLabel text = new JLabel();
        text.setBounds(posX, posY, 1000, 50);
        text.setForeground(color);
        text.setFont(new Font(fontFamily, fontStyle, fontSize));
        text.setHorizontalAlignment(JLabel.LEFT);
        text.setText(message);

        return text;
    }


}
