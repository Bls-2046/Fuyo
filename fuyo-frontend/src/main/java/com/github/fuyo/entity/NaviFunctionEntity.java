package com.github.fuyo.entity;

import com.github.fuyo.utils.layout.RUILabel;
import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Data
public class NaviFunctionEntity {
    private String functionName;
    private boolean active;
    private JLayeredPane targetPanel;
    private int[] position;
    private String iconName;

    private JLabel selector;
    private JLabel icon;
    private JLabel name;
    @Getter
    private JButton functionButton;

    public NaviFunctionEntity(String functionName, boolean active, JLayeredPane targetPanel, int[] position, String iconName) {
        this.functionName = functionName;
        this.active = active;
        this.targetPanel = targetPanel;
        this.position = position;
        this.iconName = iconName;
    }

    public void addToPanel() {
        int navi1posX = position[0];
        int navi1posY = position[1];
        RUILabel labelNaviSel = new RUILabel("mainFrame","naviActivate.png");
        JLabel navi = labelNaviSel.imageLabel(navi1posX, navi1posY);
        navi.setVisible(active);

        // We will check if function is selected or not.
        RUILabel labelNaviIcon = new RUILabel("mainFrame/" + ((active) ? "iconActivate" : "iconDeActivate"),iconName);
        JLabel naviIcon = labelNaviIcon.imageLabel(navi1posX+18, navi1posY+15);
        JLabel naviName = RUILabel.getEmptyTextLabel(navi1posX+50,navi1posY-3,functionName,18,"微软雅黑", (active) ? Color.WHITE : Color.BLACK);
        naviName.setFont(naviName.getFont().deriveFont(Font.PLAIN));
        JButton functionBtn = emptyButtonLayer();

        // Add stubs to object
        this.selector = navi;
        this.icon = naviIcon;
        this.name = naviName;
        this.functionButton = functionBtn;

        // Add stubs to layer
        targetPanel.add(navi, JLayeredPane.PALETTE_LAYER);
        targetPanel.add(naviIcon, JLayeredPane.POPUP_LAYER);
        targetPanel.add(naviName, JLayeredPane.POPUP_LAYER);
        targetPanel.add(functionButton, JLayeredPane.POPUP_LAYER);
    }

    private JButton emptyButtonLayer(){

        JButton button = new JButton();
        button.setOpaque(false);
        button.setBackground(new Color(0,0,0,0));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBounds(position[0], position[1], 215, 53); // fixed
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    public void setDeActivate() {

        // Selector
        selector.setVisible(false);

        // Icon To Black
        ImageIcon deActivateIcon = new RUILabel("mainFrame/iconDeActivate",iconName).getImageIcon();
        icon.setIcon(deActivateIcon);

        // Name to BlackColor
        name.setForeground(Color.DARK_GRAY);

        this.active = false;

    }

    public void setActivate() {

        // Selector
        selector.setVisible(true);

        // Icon To Black
        ImageIcon activateIcon = new RUILabel("mainFrame/iconActivate",iconName).getImageIcon();
        icon.setIcon(activateIcon);

        // Name to BlackColor
        name.setForeground(Color.WHITE);

        this.active = true;

    }

    public boolean getActive() {
        return active;
    }

    public void addActionListener(ActionListener actionListener) {
        functionButton.addActionListener(actionListener);
    }

}
