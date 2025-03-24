package com.github.fuyo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 课表
 */
@Data
@AllArgsConstructor
public class TabletimeEntity {
    private String keyID;       // 课程唯一标识
    private String clazz;       // 课程名
    private int x;              // 星期几
    private int y;              // 第几节
    private int beginDay;       // 课程当天上课时间
    private int endDay;         // 课程当天下课时间
    private String WeekType;    // 单双周
    private String place;       // 上课地点
    private int startWeek;      // 课程本学期开课时间
    private int finishWeek;     // 课程本学期结课时间
}
