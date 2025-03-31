package com.github.backend.service.impl.Impls;

import com.github.backend.utils.Https;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

public class DeepseekChat {
    public static void main(String[] args) {
        // 1. 准备请求数据
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

        // 2. 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("Authorization", "Bearer sk-405a7c19361140ac83fcfac30187d0a3");

        // 3. 发送请求（自动处理SSL和响应解析）
        JSONObject response = Https.post(
                "https://api.deepseek.com/chat/completions",
                requestBody,
                headers
        );

        // 4. 处理响应
        System.out.println("API 响应: " + response);
    }
}
