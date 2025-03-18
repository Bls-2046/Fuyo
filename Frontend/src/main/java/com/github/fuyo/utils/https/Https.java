package com.github.fuyo.utils.https;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Https {
    private Https() {}

    private static final OkHttpClient client;
    private static final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 发送通用的 POST 请求
     *
     * @param url    请求的 URL
     * @param object 请求体中的对象，将被序列化为 JSON
     * @param clazz  响应数据的类型
     * @param <T>    响应数据的泛型类型
     * @return 解析后的 Java 对象
     * @throws IOException 如果发生 I/O 错误
     */
    public static <T> T post(String url, Object object, Class<T> clazz) throws IOException {
        String json = gson.toJson(object);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code());
            }
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseBodyString = responseBody.string();
                return gson.fromJson(responseBodyString, clazz);
            } else {
                throw new IOException("Response body is null");
            }
        }
    }
}
