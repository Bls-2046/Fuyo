package com.github.dto.deepseek;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeepseekChatResponse {
    private int status;
    private String message;
    private String response;
}
