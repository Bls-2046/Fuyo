package com.github.backend.service.impl.Impls;

import com.github.backend.repository.mysql.YiyanRepository;
import com.github.dto.thirdPartyAPI.FetchWeatherResponse;
import com.github.backend.service.ThirdPartyApiService;
import com.github.backend.utils.Https;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@Component
public class ThirdPartyApiServiceImp implements ThirdPartyApiService {
    private final YiyanRepository yiyanRepository;

    @Autowired
    private ThirdPartyApiServiceImp(YiyanRepository yiyanRepository) {
        this.yiyanRepository = yiyanRepository;
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 高德天气 API ////////////////////////////////////////////
    /**
     * 珠海香洲区天气
     * <a href="https://lbs.amap.com/api/webservice/guide/api/weatherinfo/#t1">天气 API 文档</a>
     */
    private static String WEATHER_KEY; // 高德天气 API KEY

    @Override
    public FetchWeatherResponse.Live fetchWeather() {
        FetchWeatherResponse.Live weatherLiveResponse = new FetchWeatherResponse.Live();

        final String url = "https://restapi.amap.com/v3/weather/weatherInfo";
        final String city = "440402"; // 珠海香洲区城市编号

        Map<String, String> params = new HashMap<>();
        params.put("key", WEATHER_KEY);
        params.put("city", city);
        params.put("output", "JSON");

        JSONObject live = Https.get(url, params, null);

        if (Objects.equals(live.getString("status"), "1")) {
            // 获取 lives 数组
            JSONArray livesArray = live.getJSONArray("lives");
            if (!livesArray.isEmpty()) {
                // 获取 lives 数组的第一个对象
                JSONObject liveObject = livesArray.getJSONObject(0);

                // 设置 WeatherResponse.Live 对象的字段
                weatherLiveResponse.setProvince(liveObject.getString("province"));
                weatherLiveResponse.setCity(liveObject.getString("city"));
                weatherLiveResponse.setWeather(liveObject.getString("weather"));
                weatherLiveResponse.setTemperature(liveObject.getString("temperature"));
                weatherLiveResponse.setWinddirection(liveObject.getString("winddirection"));
                weatherLiveResponse.setWindpower(liveObject.getString("windpower"));
                weatherLiveResponse.setHumidity(liveObject.getString("humidity"));
                weatherLiveResponse.setReporttime(liveObject.getString("reporttime"));
                weatherLiveResponse.setTemperature_float(liveObject.getString("temperature_float"));
                weatherLiveResponse.setHumidity_float(liveObject.getString("humidity_float"));
            } else {
                log.warn("Lives array is empty.");
                throw new RuntimeException("No weather data found.");
            }
        } else {
            log.warn("API returned status: {}", live.getString("status"));
            throw new RuntimeException("Failed to fetch weather data.");
        }

        return weatherLiveResponse;
    }
// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 一言 API //////////////////////////////////////////////

    /**
     * 随机获得一个句子
     * @return String
     */
    @Override
    public String fetchOneSentence() {
        return yiyanRepository.findRandomSentence()
                .orElse("暂无数据");
    }
// =================================================================================================

    @PostConstruct
    public void init() {
        WEATHER_KEY = weatherKey;
    }

    @Value("${weather.key:default_value}")
    private String weatherKey;
}
