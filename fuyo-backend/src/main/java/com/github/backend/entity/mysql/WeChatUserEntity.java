package com.github.backend.entity.mysql;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "wechat_user")
public class WeChatUserEntity {
    public WeChatUserEntity() {
        this.id = UUID.randomUUID().toString();
    }

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "openid", unique = true, nullable = false, length = 128)
    private String openid; // 用户的唯一标识

    @Column(name = "nickname", length = 128)
    private String nickname; // 用户昵称

    @Column(name = "sex")
    private Integer sex; // 用户性别：1 男性，2 女性，0 未知

    @Column(name = "language", length = 32)
    private String language; // 用户语言，如 "zh_CN"

    @Column(name = "city", length = 64)
    private String city; // 用户所在城市

    @Column(name = "province", length = 64)
    private String province; // 用户所在省份

    @Column(name = "country", length = 64)
    private String country; // 用户所在国家

    @Column(name = "headimgurl", length = 512)
    private String headimgurl; // 用户头像 URL

    @Column(name = "subscribe_time")
    private Long subscribeTime; // 用户关注时间（时间戳）

    @Column(name = "unionid", length = 128)
    private String unionid; // 用户的 UnionID（如果公众号绑定到开放平台）

    @Column(name = "remark", length = 128)
    private String remark; // 公众号运营者对粉丝的备注

    @Column(name = "groupid")
    private Integer groupid; // 用户所在的分组 ID

    @Column(name = "tagid_list", length = 512)
    private String tagidList; // 用户被打上的标签 ID 列表（JSON 格式）

    @Column(name = "subscribe_scene", length = 64)
    private String subscribeScene; // 用户关注的渠道来源

    @Column(name = "qr_scene")
    private Integer qrScene; // 二维码扫码场景（开发者自定义）

    @Column(name = "qr_scene_str", length = 128)
    private String qrSceneStr; // 二维码扫码场景描述（开发者自定义）
}
