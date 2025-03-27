package com.github.fuyo.view.navigation.schedule;

import com.github.fuyo.controller.ScheduleController;
import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.utils.layout.RUILabel;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class EventDashWidget extends JLayeredPane {

    private List<ScheduleEntity> events;

    @Getter
    private List<JButton> buttons;

    public EventDashWidget(List<ScheduleEntity> events) {

        this.events = events;
        initWidget();

    }

    private void initWidget() {

        buttons = new ArrayList<>();

        // Fixed
        setBounds(71, 492, 901, 227);
        setOpaque(false);

        // cnt
        int i = 0;

        // For-i Create, each for 90 height, Maximum is 3
        for (ScheduleEntity schedule : events) {

            if (i > 2) break; // 0, 1, 2

            log.info("EventDashWidget initWidget schedule create");

            // if true, then dont show
            if (!schedule.getIsReminderInClient()) {
                // bg
                RUILabel bg = new RUILabel("mainFrame/views/reminder","EventDashContent.png");
                add(bg.imageLabel(0,90*i),POPUP_LAYER);

                // text
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String time = schedule.getDateTime().format(dtf);

                add(RUILabel.getEmptyTextLabel(17,(90*i) - 17,time,14,"微软雅黑",Color.GRAY,Font.PLAIN),POPUP_LAYER);
                add(RUILabel.getEmptyTextLabel(17,7 + (90*i), ScheduleView.wordLimitation(schedule.getTitle() + " - " + schedule.getDescription()),22,"微软雅黑",Color.GRAY,Font.BOLD),POPUP_LAYER);

                JButton button = RUILabel.getStaticEmptyLayerButton(865,5 + (90*i),36,37);
                buttons.add(button);
                add(button,POPUP_LAYER);
                button.addActionListener(e -> {
                    deleteObject(schedule);
                });

                i++;

            }

        }
    }

    private void deleteObject(ScheduleEntity schedule) {
        events.remove(schedule);
        removeAll();
        initWidget();
        revalidate();
        repaint();
        ScheduleController.deleteScheduleEventClicked(schedule);
    }

}
