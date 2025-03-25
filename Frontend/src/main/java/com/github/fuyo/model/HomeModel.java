package com.github.fuyo.model;

import com.github.fuyo.dto.WeatherResponse;
import com.github.fuyo.utils.https.Https;
import org.json.JSONObject;

public class HomeModel {
    public HomeModel() {}

    public static WeatherResponse getWeather() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setLive(new WeatherResponse.Live());

        String url = "http://127.0.0.1:8080/api/weather";

        JSONObject weather = Https.get(url, null, null);

        weatherResponse.setStatus(weather.getInt("status"));
        if (weatherResponse.getStatus() == 200) {
            JSONObject weatherLiveResponse = weather.getJSONObject("live");
            weatherResponse.getLive().setProvince(weatherLiveResponse.getString("province"));
            weatherResponse.getLive().setCity(weatherLiveResponse.getString("city"));
            weatherResponse.getLive().setWeather(weatherLiveResponse.getString("weather"));
            weatherResponse.getLive().setTemperature(weatherLiveResponse.getString("temperature"));
            weatherResponse.getLive().setWinddirection(weatherLiveResponse.getString("winddirection"));
            weatherResponse.getLive().setWindpower(weatherLiveResponse.getString("windpower"));
            weatherResponse.getLive().setHumidity(weatherLiveResponse.getString("humidity"));
            weatherResponse.getLive().setReporttime(weatherLiveResponse.getString("reporttime"));
            weatherResponse.getLive().setTemperature_float(weatherLiveResponse.getString("temperature_float"));
            weatherResponse.getLive().setHumidity_float(weatherLiveResponse.getString("humidity_float"));
        }

        return weatherResponse;
    }

    /**
     * 每日一言
     * @return String
     */
    public static String getOneSentence() {
        String url = "http://127.0.0.1:8080/api/yiyan";
        JSONObject response = Https.get(url, null, null);
        return response.getString("sentence");
    }
}
