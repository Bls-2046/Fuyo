package com.github.fuyo.utils.https;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
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

    private static final RestTemplate restTemplate;

    static {
        // 初始化 RestTemplate
        restTemplate = new RestTemplate();

        // 配置 SSL 忽略证书验证（仅用于测试环境）
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            }}, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to configure SSL", e);
        }
    }

    /**
     * 发送 GET 请求，返回 JSON 数据
     *
     * @param url    请求 URL
     * @param params 请求参数（可选）
     * @param headers 请求头（可选）
     * @return JSON 格式的响应数据
     */
    public static JSONObject get(String url, Map<String, String> params, HttpHeaders headers) {
        // 构建 URL 和查询参数
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        // 创建 HTTP 实体
        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        // 发送 GET 请求
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        // 将响应体转换为 JSONObject
        return new JSONObject(response.getBody());
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
