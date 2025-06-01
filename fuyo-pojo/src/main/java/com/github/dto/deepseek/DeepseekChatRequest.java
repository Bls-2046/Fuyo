package com.github.dto.deepseek;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DeepseekChatRequest {
    private String username;
    private List<Dialogue> dialogueList;

    @Data
    @Accessors(chain = true)
    static class Dialogue {
        private String role;
        private String content;
    }
}
