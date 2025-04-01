package com.github.fuyo.view.navigation.clazz;

import com.github.fuyo.dto.schedule.AddScheduleRequest;
import com.github.fuyo.entity.TabletimeEntity;
import com.github.fuyo.utils.layout.RUILabel;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class ClazzView extends JLayeredPane {

    private final static int posYFix = 10;
    // ManuWired
    private List<TabletimeEntity> tabletimeEntity;

    public ClazzView(List<TabletimeEntity> tabletimeEntity) {
        this.tabletimeEntity = tabletimeEntity;
        setBounds(260, 0, 1100, 768);

        RUILabel bgLabel = new RUILabel("mainFrame/views/clazz", "bg.png");
        add(bgLabel.imageLabel(65, 37), DEFAULT_LAYER);

        // Function Implement
        tabletimeEntity.sort(new Comparator<TabletimeEntity>() {
            @Override
            public int compare(TabletimeEntity o1, TabletimeEntity o2) {
                return o1.getBeginDay() - o2.getBeginDay();
            }
        });

        // Current Time?
        LocalTime now = LocalTime.now();

        TabletimeEntity currentClazz = null;
        TabletimeEntity nextClazz = null;

        // Remaining Clazz
        int remainingClazzCount = 0;
        for (TabletimeEntity clazz : tabletimeEntity) {
            LocalTime startTime = CourseTime()[clazz.getBeginDay() * 2 - 2];
            if (now.isBefore(startTime)) {
                remainingClazzCount++;
            }
        }

        // For next clazz
        for (TabletimeEntity clazz : tabletimeEntity) {
            int[] courseIdx = new int[2];
            courseIdx[0] = clazz.getBeginDay();
            courseIdx[1] = clazz.getEndDay();
            LocalTime startTime = CourseTime()[courseIdx[0] * 2 - 2];
            LocalTime endTime = CourseTime()[courseIdx[1] * 2 - 1];

            if (now.isAfter(startTime) && now.isBefore(endTime)) {
                currentClazz = clazz;
            } else if (now.isBefore(startTime)) {
                if (nextClazz == null || startTime.isBefore(CourseTime()[nextClazz.getBeginDay() * 2 - 2])) {
                    nextClazz = clazz;
                }
            }
        }

        // Current Clazz
        String currentClazzTime = currentClazz != null ? getTimeString(currentClazz.getBeginDay())[0] + " - " + getTimeString(currentClazz.getEndDay())[1] : "";
        String currentClazzName = currentClazz != null ? currentClazz.getClazz() : (remainingClazzCount == 0) ? "休憩时间" : (remainingClazzCount == tabletimeEntity.size()) ? "准备上课~" : "课间休息~";
        String currentClazzPlace = currentClazz != null ? currentClazz.getPlace() : "";

        add(RUILabel.getEmptyTextLabel(102, 152 - posYFix, currentClazzTime, 30, "微软雅黑", Color.GRAY, Font.PLAIN), POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(100, 192 - posYFix, currentClazzName, 48, "微软雅黑", Color.GRAY, Font.PLAIN), POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(148, 315 - posYFix, currentClazzPlace, 22, "微软雅黑", Color.GRAY, Font.PLAIN), POPUP_LAYER);

        // Next Clazz
        String nextClazzTime = nextClazz != null ? getTimeString(nextClazz.getBeginDay())[0] + " - " + getTimeString(nextClazz.getEndDay())[1] : "";
        String nextClazzName = nextClazz != null ? nextClazz.getClazz() : "今日无事";
        String nextClazzPlace = nextClazz != null ? nextClazz.getPlace() : "";

        add(RUILabel.getEmptyTextLabel(102, 507 - posYFix, nextClazzTime, 30, "微软雅黑", Color.GRAY, Font.PLAIN), POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(100, 547 - posYFix, nextClazzName, 48, "微软雅黑", Color.GRAY, Font.PLAIN), POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(148, 670 - posYFix, nextClazzPlace, 22, "微软雅黑", Color.GRAY, Font.PLAIN), POPUP_LAYER);

        // Remaining
        add(RUILabel.getEmptyTextLabel(629, 540, ""+remainingClazzCount, 48, "微软雅黑", Color.GRAY, Font.PLAIN), POPUP_LAYER);
        add(RUILabel.getEmptyTextLabel(629+40, 540, "节课", 48, "微软雅黑", Color.GRAY, Font.PLAIN), POPUP_LAYER);
    }

    // Stupid way
    private static String[] getTimeString(int idx) {
        switch (idx) {
            case 1 -> {
                return new String[]{"08:00", "08:45"};
            }
            case 2 -> {
                return new String[]{"08:50", "09:35"};
            }
            case 3 -> {
                return new String[]{"09:55", "10:40"};
            }
            case 4 -> {
                return new String[]{"10:45", "11:30"};
            }
            case 5 -> {
                return new String[]{"11:35", "12:20"};
            }
            case 6 -> {
                return new String[]{"13:20", "14:05"};
            }
            case 7 -> {
                return new String[]{"14:10", "14:55"};
            }
            case 8 -> {
                return new String[]{"15:15", "16:00"};
            }
            case 9 -> {
                return new String[]{"16:05", "16:50"};
            }
            case 10 -> {
                return new String[]{"16:55", "17:40"};
            }
            case 11 -> {
                return new String[]{"18:30", "19:15"};
            }
            case 12 -> {
                return new String[]{"19:20", "20:05"};
            }
            case 13 -> {
                return new String[]{"20:10", "20:55"};
            }
        }
        return null; // Invaild Time
    }

    private static LocalTime[] CourseTime() {

        // 定义课程时间段及对应的ID
        LocalTime[] courseTimes = {
                LocalTime.of(8, 0), LocalTime.of(8, 45),
                LocalTime.of(8, 50), LocalTime.of(9, 35),
                LocalTime.of(9, 55), LocalTime.of(10, 40),
                LocalTime.of(10, 45), LocalTime.of(11, 30),
                LocalTime.of(11, 35), LocalTime.of(12, 20),
                LocalTime.of(13, 20), LocalTime.of(14, 5),
                LocalTime.of(14, 10), LocalTime.of(14, 55),
                LocalTime.of(15, 15), LocalTime.of(16, 0),
                LocalTime.of(16, 5), LocalTime.of(16, 50),
                LocalTime.of(16, 55), LocalTime.of(17, 40),
                LocalTime.of(18, 30), LocalTime.of(19, 15),
                LocalTime.of(19, 20), LocalTime.of(20, 5),
                LocalTime.of(20, 10), LocalTime.of(20, 55),
        };

        return courseTimes;
    }

}
