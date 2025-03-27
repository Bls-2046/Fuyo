package com.github.backend.service;

import com.github.backend.dto.api.FetchWeatherResponse;

/**
 * 第三方 API 使用方法接口
 */
public interface ThirdPartyApiService {
    /**
     * 高德天气 API
     * 获得广东省珠海市香洲区实时天气
     * @return WeatherResponse.Live
     */
    FetchWeatherResponse.Live fetchWeather();

    /**
     * 一言 API
     * 获得随机获得一句话
     * @return String
     */
    String fetchOneSentence();
}
