package com.github.fuyo.utils.https;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    // 基础默认headers（不可变）
    public static final Map<String, String> DEFAULT_HEADERS = Map.of(
            "Content-Type", "application/json",
            "Accept", "application/json"
    );

    /**
     * 获取基础默认headers（不可修改）
     */
    public static Map<String, String> basic() {
        return DEFAULT_HEADERS;
    }

    /**
     * 合并自定义headers与默认headers
     */
    public static Map<String, String> merge(Map<String, String> customHeaders) {
        Map<String, String> merged = new HashMap<>(DEFAULT_HEADERS);
        if (customHeaders != null) {
            merged.putAll(customHeaders);
        }
        return Collections.unmodifiableMap(merged);
    }
}
