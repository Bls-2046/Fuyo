package com.github.backend.service;

import com.github.backend.dto.api.WeatherResponse;

public interface ThirdPartyApiService {
    WeatherResponse getWeather();
    String getOneSentence();
}
