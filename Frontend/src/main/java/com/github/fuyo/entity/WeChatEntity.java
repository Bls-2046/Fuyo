package com.github.fuyo.entity;

import lombok.Data;

/**
 * 用户微信号基本信息
 */
@Data
public class WeChatEntity {
    private String openid;              // 用户的唯一标识
    private String nickname;            // 用户昵称
    private Integer sex;                // 用户性别：1 男性，2 女性，0 未知
    private String language;            // 用户语言，如 "zh_CN"
    private String city;                // 用户所在城市
    private String province;            // 用户所在省份
    private String country;             // 用户所在国家
    private String headimgurl;          // 用户头像 URL
    private Long subscribeTime;         // 用户关注时间（时间戳）
    private String unionid;             // 用户的 UnionID（如果公众号绑定到开放平台）
    private String remark;              // 公众号运营者对粉丝的备注
    private Integer groupid;            // 用户所在的分组 ID
    private String tagidList;           // 用户被打上的标签 ID 列表（JSON 格式）
    private String subscribeScene;      // 用户关注的渠道来源
    private Integer qrScene;            // 二维码扫码场景（开发者自定义）
    private String qrSceneStr;          // 二维码扫码场景描述（开发者自定义）
}
