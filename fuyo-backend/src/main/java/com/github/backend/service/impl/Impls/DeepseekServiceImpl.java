package com.github.backend.service.impl.Impls;

import com.github.backend.service.DeepseekService;
import com.github.backend.utils.Https;
import com.github.dto.deepseek.DeepseekChatRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class DeepseekServiceImpl implements DeepseekService {

    @Override
    public String deepseekChatRespo(DeepseekChatRequest deepseekChatRequest) {
        JSONObject requestBody = new JSONObject()
                .put("messages", new JSONArray()
                        .put(new JSONObject()
                                .put("content", "You are a helpful assistant")
                                .put("role", "system"))
                        .put(new JSONObject()
                                .put("content", "Hi")
                                .put("role", "user")))
                .put("model", "deepseek-chat")
                .put("frequency_penalty", 0)
                .put("max_tokens", 2048)
                .put("presence_penalty", 0)
                .put("response_format", new JSONObject()
                        .put("type", "text"))
                .put("stop", JSONObject.NULL)
                .put("stream", false)
                .put("stream_options", JSONObject.NULL)
                .put("temperature", 1)
                .put("top_p", 1)
                .put("tools", JSONObject.NULL)
                .put("tool_choice", "none")
                .put("logprobs", false)
                .put("top_logprobs", JSONObject.NULL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("Authorization", "Bearer sk-405a7c19361140ac83fcfac30187d0a3");

        JSONObject response = Https.post(
                "https://api.deepseek.com/chat/completions",
                requestBody,
                headers
        );

        return "";
    }
}
