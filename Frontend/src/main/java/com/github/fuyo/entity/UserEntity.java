package com.github.fuyo.entity;

import lombok.Data;

import java.util.List;
import java.util.Collections;

/**
 * ==================================================
 * 用户类
 * 用于存放用户的个人基本信息包含以下参数:
 * {
 *     "username" 用户名
 *     "name": 姓名
 *     "department" 年级专业
 *     "email" 邮箱
 *     "phone" 手机号
 *     "cookie" 用于获取课表
 *     "tabletime" 课表 {
 *         "keyID" 课程唯一标识
 *         "clazz" 课程名
 *         "x" 横轴：周次
 *         "y" 纵轴：第几节课
 *         "beginDay" 课程当天上课时间
 *         "endDay" 课程当天下课时间
 *         "WeekType" 单双周
 *         "place" 上课地点
 *         "startWeek" 本学期该门课开课时间
 *         "finishWeek" 本学期该门课结课时间
 *     }
 * }
 * ==================================================
 */
@Data
public class UserEntity {
    private static volatile UserEntity userInformation;

    public UserEntity() {
        this.tabletimeEntity = Collections.emptyList();
    }

    private String username;                // 用户名
    private String name;                    // 姓名
    private String department;              // 年级专业
    private String email;                   // 邮箱
    private String phone;                   // 手机号
    private String cookie;

    private List<TabletimeEntity> tabletimeEntity;      // 课表
    private List<ScheduleEntity> schedule;  // 日程安排
    private WeChatEntity wechatUser;        // 微信用户基本信息

    /**
     * 公共静态方法，提供全局访问点
     * @return UserEntity
     */
    public static UserEntity getUserInformation() {
        if (userInformation == null) {
            synchronized (UserEntity.class) {
                if (userInformation == null) {
                    userInformation = new UserEntity();
                }
            }
        }
        return userInformation;
    }

    /**
     * 禁止实例被 Clone
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton class, cloning not allowed");
    }

    /**
     * 清空当前用户的数据
     */
    public static void clearUserInformation() {
        userInformation = null;
    }
}
