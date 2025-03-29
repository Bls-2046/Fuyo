package com.github.fuyo.utils.https;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Https {
    private Https() {}

    private static final OkHttpClient client;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    static {
        // 配置SSL忽略验证（仅开发环境）
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                }
        };

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager)trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to configure SSL", e);
        }
    }

    /**
     * 发送GET请求
     * @param url 请求URL
     * @param params 查询参数
     * @param headers 请求头
     * @param responseType 响应类型
     * @return 响应对象
     */
    public static <T> T get(String url,
                            Map<String, String> params,
                            HttpHeaders headers,
                            Class<T> responseType) throws IOException {
        // 构建URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        if (params != null) {
            params.forEach(builder::queryParam);
        }

        // 构建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(builder.build().toUriString())
                .get();

        addHeaders(requestBuilder, headers);

        return executeRequest(requestBuilder.build(), responseType);
    }

    /**
     * 发送POST请求
     * @param url 请求URL
     * @param body 请求体对象
     * @param headers 请求头
     * @param responseType 响应类型
     * @return 响应对象
     */
    public static <T> T post(String url,
                             Object body,
                             HttpHeaders headers,
                             Class<T> responseType) throws IOException {
        return sendBodyRequest(url, body, headers, responseType, "POST");
    }

    /**
     * 发送PUT请求
     * @param url 请求URL
     * @param body 请求体对象
     * @param headers 请求头
     * @param responseType 响应类型
     * @return 响应对象
     */
    public static <T> T put(String url,
                            Object body,
                            HttpHeaders headers,
                            Class<T> responseType) throws IOException {
        return sendBodyRequest(url, body, headers, responseType, "PUT");
    }

    /**
     * 发送DELETE请求
     * @param url 请求URL
     * @param body 请求体对象（可选）
     * @param headers 请求头
     * @param responseType 响应类型
     * @return 响应对象
     */
    public static <T> T delete(String url,
                               Object body,
                               HttpHeaders headers,
                               Class<T> responseType) throws IOException {
        return sendBodyRequest(url, body, headers, responseType, "DELETE");
    }

    // ================ 私有方法 ================

    private static <T> T sendBodyRequest(String url,
                                         Object body,
                                         HttpHeaders headers,
                                         Class<T> responseType,
                                         String method) throws IOException {
        try {
            String json = objectMapper.writeValueAsString(body);
            RequestBody requestBody = RequestBody.create(json, JSON);

            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .method(method, requestBody);

            addHeaders(requestBuilder, headers);

            return executeRequest(requestBuilder.build(), responseType);
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to serialize request body", e);
        }
    }

    private static void addHeaders(Request.Builder builder, HttpHeaders headers) {
        if (headers != null) {
            headers.forEach((name, values) ->
                    values.forEach(value -> builder.addHeader(name, value))
            );
        }
        // 默认头
        builder.addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json");
    }

    private static <T> T executeRequest(Request request, Class<T> responseType) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP " + response.code() + ": " +
                        (response.body() != null ? response.body().string() : ""));
            }

            ResponseBody body = response.body();
            if (body != null) {
                return objectMapper.readValue(body.string(), responseType);
            }
            throw new IOException("Empty response body");
        }
    }
}