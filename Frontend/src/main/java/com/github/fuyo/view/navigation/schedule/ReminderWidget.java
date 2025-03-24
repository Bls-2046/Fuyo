package com.github.fuyo.view.navigation.schedule;

import com.github.fuyo.utils.layout.RUILabel;
import lombok.Data;
import lombok.Getter;

import javax.swing.*;

@Data
public class ReminderWidget extends JLayeredPane {

    @Getter
    private int type = 1; // default -> day

    private JLabel bgLabel;

    public ReminderWidget() {

        // Fixed
        setBounds(699, 118, 300, 37);
        setOpaque(false);

        changeBg();

        // JButton
        JButton dayButton = RUILabel.getStaticEmptyLayerButton(0, 0, 92, 37);
        add(dayButton,POPUP_LAYER);

        JButton hrsButton = RUILabel.getStaticEmptyLayerButton(104, 0, 92, 37);
        add(hrsButton,POPUP_LAYER);

        JButton minButton = RUILabel.getStaticEmptyLayerButton(208, 0, 92, 37);
        add(minButton,POPUP_LAYER);

        dayButton.addActionListener(e -> {
            type = 1;
            changeBg();
        });

        hrsButton.addActionListener(e -> {
            type = 2;
            changeBg();
        });

        minButton.addActionListener(e -> {
            type = 3;
            changeBg();
        });

    }

    private void changeBg(){

        if (bgLabel != null){
            remove(bgLabel);
            revalidate();
            repaint();
        }

        RUILabel image;
        switch (type) {
            case 1 -> {
                image = new RUILabel("mainFrame/views/reminder", "Reminder_Before_Day.png");
            }
            case 2 -> {
                image = new RUILabel("mainFrame/views/reminder", "Reminder_Before_Hrs.png");
            }
            case 3 -> {
                image = new RUILabel("mainFrame/views/reminder", "Reminder_Before_Minute.png");
            }
            default -> {
                throw new RuntimeException("ReminderWidget->Invalid type: " + type);
            }
        }

        bgLabel = image.imageLabel(0, 0);
        add(bgLabel,DEFAULT_LAYER);
        revalidate();
        repaint();
    }

}
