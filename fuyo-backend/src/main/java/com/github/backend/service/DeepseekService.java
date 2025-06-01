package com.github.backend.service;

import com.github.dto.deepseek.DeepseekChatRequest;

public interface DeepseekService {
    String deepseekChatRespo(DeepseekChatRequest deepseekChatRequest);
}
