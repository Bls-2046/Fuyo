package com.github.fuyo.view.navigation.schedule;

import com.github.fuyo.entity.ScheduleEntity;
import com.github.fuyo.entity.ScheduleViewEntity;
import com.github.fuyo.utils.layout.RUILabel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class ScheduleView extends JLayeredPane {

    // Un-Exported
    private ReminderWidget rw;
    private EventDashWidget edw;

    // Exported
    @Getter
    private ScheduleViewEntity viewEntity;

    // List, plz set it again when u add something into scheduleEntities.
    @Setter
    @Getter
    private List<ScheduleEntity> scheduleEntities;

    public ScheduleView(List<ScheduleEntity> scheduleEntities) {

        this.scheduleEntities = scheduleEntities;

        // Fixed
        setBounds(260, 0, 1100, 768);

        RUILabel bgLabel = new RUILabel("mainFrame/views/reminder", "bg.png");
        add(bgLabel.imageLabel(0, 0), DEFAULT_LAYER);

        // Input Area
        JTextField titleInput = RUILabel.getEmptyInputTextLabel(72, 118, 464, 37, "", Color.GRAY,18, "微软雅黑",Font.PLAIN);
        add(titleInput, POPUP_LAYER);

        JTextField reminderTimeInput = RUILabel.getEmptyInputTextLabel(562, 118, 112, 37, "", Color.GRAY,18, "微软雅黑",Font.PLAIN);
        add(reminderTimeInput, POPUP_LAYER);

        JTextField yearInput = RUILabel.getEmptyInputTextLabel(562, 260, 122, 37, "", Color.GRAY,18, "微软雅黑",Font.PLAIN);
        add(yearInput, POPUP_LAYER);

        JTextField monInput = RUILabel.getEmptyInputTextLabel(698, 260, 92, 37, "", Color.GRAY,18, "微软雅黑",Font.PLAIN);
        add(monInput, POPUP_LAYER);

        JTextField dayInput = RUILabel.getEmptyInputTextLabel(802, 260, 92, 37, "", Color.GRAY,18, "微软雅黑",Font.PLAIN);
        add(dayInput, POPUP_LAYER);

        JTextField hrsInput = RUILabel.getEmptyInputTextLabel(562, 330, 157, 37, "", Color.GRAY,18, "微软雅黑",Font.PLAIN);
        add(hrsInput, POPUP_LAYER);

        JTextField minInput = RUILabel.getEmptyInputTextLabel(738, 330, 157, 37, "", Color.GRAY,18, "微软雅黑",Font.PLAIN);
        add(minInput, POPUP_LAYER);

        // 4*6,6 (24,6) per line words, word, Microsoft YaHei, 18, Regular
        JTextArea reminderTextArea = RUILabel.getEmptyTextArea(88, 215, 454, 130, 24, 6, Color.GRAY, 18, "微软雅黑", Font.PLAIN);
        add(reminderTextArea, POPUP_LAYER);

        // Buttons
        // Warning: Reminder and EventDash should handle by other view class, not this clazz.
        JButton resetButton = RUILabel.getStaticEmptyLayerButton(907, 260, 92, 37);
        resetButton.addActionListener(_ -> {
            // 设置默认标题
            getViewEntity().getTitle().setText("我的日程");
            // 设置默认内容
            getViewEntity().getContent().setText("好像有什么重要的事...");
            // 将时间设置设为默认值
            getViewEntity().getRemindTime().setText("0");
            LocalDateTime now = LocalDateTime.now();
            getViewEntity().getScheduleYear().setText(String.valueOf(now.getYear()));
            getViewEntity().getScheduleMonth().setText(String.valueOf(now.getMonthValue()));
            getViewEntity().getScheduleDay().setText(String.valueOf(now.getDayOfMonth()));
            getViewEntity().getScheduleHour().setText(String.valueOf(now.getHour()));
            getViewEntity().getScheduleMinute().setText(String.valueOf(now.getMinute()));
        });
        add(resetButton, POPUP_LAYER);

        JButton submitButton = RUILabel.getStaticEmptyLayerButton(907, 330, 92, 37);
        add(submitButton,POPUP_LAYER);

        // Special Areas (Widgets)
        rw = new ReminderWidget();
        add(rw, POPUP_LAYER);

        edw = new EventDashWidget(scheduleEntities);
        add(edw, POPUP_LAYER);


        // New Entity
        viewEntity = new ScheduleViewEntity(titleInput,reminderTextArea,reminderTimeInput,
                rw,yearInput,monInput,dayInput,hrsInput,minInput,submitButton);

    }

    public void clearInput() {
        viewEntity.getTitle().setText("");
        viewEntity.getContent().setText("");
        viewEntity.getRemindTime().setText("");
        viewEntity.getScheduleYear().setText("");
        viewEntity.getScheduleMonth().setText("");
        viewEntity.getScheduleDay().setText("");
        viewEntity.getScheduleHour().setText("");
        viewEntity.getScheduleMinute().setText("");
    }

    // Event will firstly add to this view, then submit.
    public void repaintEDW(){
        remove(edw);
        log.info("\n\nNewData of scheduleEntities{}\n", scheduleEntities.toString());
        edw = new EventDashWidget(scheduleEntities);
        add(edw, POPUP_LAYER);
        revalidate();
        repaint();
    }

    public static String wordLimitation(String word) {
        String display = word;
        display = display.replaceAll("\n"," ");
        if (display.length() > 55) {
            display = display.substring(0, 55) + "...";
        }
        return display;
    }

}
