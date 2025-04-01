package com.github.dto.thirdPartyAPI;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FetchYiYanResponse {
    private int status;
    private String message;
    private String sentence;
}
