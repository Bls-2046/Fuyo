package com.github.backend.controller;

import com.github.backend.dto.api.OneSentenceResponse;
import com.github.backend.service.ThirdPartyApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class ThirdPartyAPIController {
    private final ThirdPartyApiService thirdPartyApiService;

    @Autowired
    public ThirdPartyAPIController(ThirdPartyApiService thirdPartyApiService) {
        this.thirdPartyApiService = thirdPartyApiService;
    }

    /**
     * 获得一句佳句
     * @return String
     */
    @GetMapping("/yiyan")
    public OneSentenceResponse getOneSentence() {
        String oneSentence = null;
        OneSentenceResponse oneSentenceResponse = new OneSentenceResponse();

        oneSentence = thirdPartyApiService.getOneSentence();

        if (oneSentence != null) {
            oneSentenceResponse.setStatus(200);
            oneSentenceResponse.setSentence(oneSentence);
        } else {
            oneSentenceResponse.setStatus(404);
        }
        return oneSentenceResponse;
    }
}
