package com.github.fuyo.model;

import com.github.fuyo.dto.FetchWeatherResponse;
import com.github.fuyo.dto.thirdPartyAPI.FetchYiYanResponse;
import com.github.fuyo.utils.https.Https;

import java.io.IOException;

public class HomeModel {
    public HomeModel() {}

    public static FetchWeatherResponse fetchWeather() throws IOException {
        FetchWeatherResponse fetchWeatherResponse = new FetchWeatherResponse();
        fetchWeatherResponse.setLive(new FetchWeatherResponse.Live());

        String url = "http://127.0.0.1:8080/api/weather";

        FetchWeatherResponse weather = Https.get(url, null, null, FetchWeatherResponse.class);

        if (fetchWeatherResponse.getStatus() == 200) {
            return weather;
        }

        return null;
    }

    /**
     * 每日一言
     * @return String
     */
    public static String fetchYiYan() throws IOException {
        String url = "http://127.0.0.1:8080/api/yiyan";

        FetchYiYanResponse response = Https.get(url, null, null, FetchYiYanResponse.class);

        return response.getSentence();
    }
}
