package com.github.backend.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.http.client.HttpClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.util.Map;
import java.util.Objects;

/**
 * HTTPS 请求工具类（智能类型推断版）
 * 特性：
 * 1. 所有方法自动判断返回 JSONObject/JSONArray/String
 * 2. 统一的泛型返回类型 <T>
 * 3. 保持原有 SSL 忽略功能
 * 4. 线程安全的静态工具类
 */
public final class Https {

    private static final WebClient WEB_CLIENT;

    static {
        WEB_CLIENT = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .secure(spec -> spec.sslContext(configureUnsafeSslContext()))
                ))
                .build();
    }

    private Https() {
        throw new UnsupportedOperationException("工具类不允许实例化");
    }

    // ================ 公开方法 ================

    /**
     * 智能 GET 请求（自动判断返回类型）
     * @param url     请求地址
     * @param params  查询参数
     * @param headers 请求头
     * @return 自动解析的 JSONObject/JSONArray/String
     * @param <T> 自动推断的返回类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String url,
                            Map<String, String> params,
                            HttpHeaders headers) {
        String response = executeRequest(
                WEB_CLIENT.get()
                        .uri(buildUri(url, params))
                        .headers(h -> addHeadersIfPresent(h, headers))
        );
        return (T) parseResponse(response);
    }

    /**
     * 智能 POST JSON 请求
     * @param url     请求地址
     * @param body    JSON 请求体
     * @param headers 请求头
     * @return 自动解析的响应
     * @param <T> 自动推断的返回类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T post(String url,
                                 JSONObject body,
                                 HttpHeaders headers) {
        HttpHeaders finalHeaders = initializeHeaders(headers);
        finalHeaders.setContentType(MediaType.APPLICATION_JSON);

        String response = executeRequest(
                WEB_CLIENT.post()
                        .uri(url)
                        .headers(h -> h.addAll(finalHeaders))
                        .bodyValue(body.toString())
        );
        return (T) parseResponse(response);
    }

    /**
     * 智能 POST Form 请求
     * @param url      请求地址
     * @param formData 表单数据
     * @param headers  请求头
     * @return 自动解析的响应
     * @param <T> 自动推断的返回类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T post(String url,
                                 Map<String, String> formData,
                                 HttpHeaders headers) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        formData.forEach(body::add);

        HttpHeaders finalHeaders = initializeHeaders(headers);
        finalHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        String response = executeRequest(
                WEB_CLIENT.post()
                        .uri(url)
                        .headers(h -> h.addAll(finalHeaders))
                        .bodyValue(body)
        );
        return (T) parseResponse(response);
    }

    // ================ 私有工具方法 ================

    private static SslContext configureUnsafeSslContext() {
        try {
            return SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("SSL 配置失败", e);
        }
    }

    private static String buildUri(String url, Map<String, String> params) {
        validateUrl(url);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        if (params != null) {
            params.forEach(builder::queryParam);
        }
        return builder.build().toUriString();
    }

    private static String executeRequest(WebClient.RequestHeadersSpec<?> spec) {
        return spec.retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private static Object parseResponse(String rawResponse) {
        if (rawResponse == null || rawResponse.trim().isEmpty()) {
            return null;
        }

        String trimmed = rawResponse.trim();
        if (trimmed.startsWith("{")) {
            return new JSONObject(trimmed);
        } else if (trimmed.startsWith("[")) {
            return new JSONArray(trimmed);
        }
        return trimmed;
    }

    private static HttpHeaders initializeHeaders(HttpHeaders headers) {
        return headers != null ? headers : new HttpHeaders();
    }

    private static void addHeadersIfPresent(HttpHeaders target, HttpHeaders source) {
        if (source != null) {
            target.addAll(source);
        }
    }

    private static void validateUrl(String url) {
        Objects.requireNonNull(url, "URL 不能为 null");
        if (url.isBlank()) {
            throw new IllegalArgumentException("URL 不能为空");
        }
    }
}