package com.github.fuyo.model;

import com.github.dto.thirdPartyAPI.FetchWeatherResponse;
import com.github.dto.thirdPartyAPI.FetchYiYanResponse;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HomeModel {
    public HomeModel() {}

    public static FetchWeatherResponse fetchWeather() throws IOException {
        FetchWeatherResponse fetchWeatherResponse = new FetchWeatherResponse();
        fetchWeatherResponse.setLive(new FetchWeatherResponse.Live());

        String url = "http://127.0.0.1:8080/api/fetch/weather";

        FetchWeatherResponse weather = Https.get(url, null, null, FetchWeatherResponse.class);

        log.info("HomeModel Fetch weather: {}", weather);

        return weather;
    }

    /**
     * 每日一言
     * @return String
     */
    public static String fetchYiYan() throws IOException {
        String url = "http://127.0.0.1:8080/api/fetch/yiyan";

        FetchYiYanResponse response = Https.get(url, null, null, FetchYiYanResponse.class);

        return response.getSentence();
    }
}
