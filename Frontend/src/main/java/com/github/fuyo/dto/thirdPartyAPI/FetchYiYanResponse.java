package com.github.fuyo.dto.thirdPartyAPI;

import lombok.Data;
import lombok.Getter;

@Data
public class FetchYiYanResponse {
    private int status;
    private String message;
    private String sentence;
}
