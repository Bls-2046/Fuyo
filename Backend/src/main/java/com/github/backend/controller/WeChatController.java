package com.github.backend.controller;

import com.github.backend.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/wechat")
@Validated
public class WeChatController {
    private final WeChatService wechatService;

    @Autowired
    public WeChatController(WeChatService wechatService) {
        this.wechatService = wechatService;
    }


    public void verifyWeChatUser() {

    }
}
