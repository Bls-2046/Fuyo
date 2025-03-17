package com.github.fuyo.utils.layout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RUILogin extends JFrame {

    private String fileType;
    private String fileName;

    public JLabel imageLabel(int posX, int posY) {

        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("images/layout/%s/%s", fileType, fileName)))
        );

        JLabel label = new JLabel(icon);
        label.setOpaque(false);
        label.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());

        return label;
    };

    public JTextField textLabel(int posX, int posY, String message, Color color, int fontSize, String fontFamily) {
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("images/layout/%s/%s", fileType, fileName)))
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

    public JPasswordField passwordLabel(int posX, int posY, String message, Color color, int fontSize, String fontFamily) {
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("images/layout/%s/%s", fileType, fileName)))
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

    public JButton buttonLabel(int posX, int posY) {

        // Button ICON UnPressed
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("images/layout/%s/%s", fileType, fileName)))
        );

        // Button ICON Pressed
        String pressedFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String pressedFileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        // log.info("ResultNameCheck @ RUI.ButtonLabel: {} ", pressedFileName + "Pressed." + pressedFileType);
        ImageIcon iconPressed = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("images/layout/%s/%s", fileType,
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

    public JLabel[] errorLabel(int posX, int posY, String message, Color color, int fontSize, String fontFamily) {
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("images/layout/%s/%s", fileType, fileName)))
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
}
