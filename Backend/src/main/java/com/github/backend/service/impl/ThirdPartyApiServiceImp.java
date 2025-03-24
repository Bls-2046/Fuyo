package com.github.backend.service.impl;

import com.github.backend.dto.api.WeatherResponse;
import com.github.backend.service.ThirdPartyApiService;
import com.github.backend.utils.Https;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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
// =================================================================================================
// ///////////////////////////////////////// 高德天气 API ////////////////////////////////////////////
    /**
     * 珠海香洲区天气
     * <a href="https://lbs.amap.com/api/webservice/guide/api/weatherinfo/#t1">天气 API 文档</a>
     */
    private static String WEATHER_KEY; // 高德天气 API KEY

    @Override
    public WeatherResponse.Live getWeather() {
        WeatherResponse.Live weatherLiveResponse = new WeatherResponse.Live();

        final String url = "https://restapi.amap.com/v3/weather/weatherInfo";
        final String city = "440402"; // 珠海香洲区城市编号

        Map<String, String> params = new HashMap<>();
        params.put("key", WEATHER_KEY);
        params.put("city", city);
        params.put("output", "JSON");

        JSONObject live = Https.get(url, params, null);

        if (Objects.equals(live.getString("status"), "1")) {
            weatherLiveResponse.setProvince(live.getString("province"));
            weatherLiveResponse.setCity(live.getString("city"));
            weatherLiveResponse.setWeather(live.getString("weather"));
            weatherLiveResponse.setTemperature(live.getString("temperature"));
            weatherLiveResponse.setWinddirection(live.getString("winddirection"));
            weatherLiveResponse.setWindpower(live.getString("windpower"));
            weatherLiveResponse.setHumidity(live.getString("humidity"));
            weatherLiveResponse.setReporttime(live.getString("reporttime"));
        }

        return weatherLiveResponse;
    }
// =================================================================================================
// ///////////////////////////////////////// 一言 API ///////////////////////////////////////////////

    private static String MEI_RI_YI_YAN_KEY; // 每日一言 API KEY:
    private static String MEI_RI_YING_YU_KEY; // 每日英语 API KEY

    /**
     * 随机获得一个句子
     * @return String
     */
    @Override
    public String getOneSentence() {
        int randomNumber = (int) (Math.random() * 10000) % 2 + 1;

        String sentence = "";

        if (randomNumber == 1) {
            sentence = getChineseSentence();
        } else if (randomNumber == 2) {
            sentence = getEnglishSentence();
        }
        return sentence;
    }

    /**
     * 每日一言
     * @return String
     */
    private String getChineseSentence() {
        String url = "https://whyta.cn/api/yiyan";
        Map<String, String> params = new HashMap<>();

        params.put("key", MEI_RI_YI_YAN_KEY);

        JSONObject ontSentence = Https.get(url, params, null);

        return ontSentence.getString("hitokoto");
    }

    /**
     * 每日英语
     * @return String
     */
    private String getEnglishSentence() {
        String url = "https://whyta.cn/api/tx/everyday";
        Map<String, String> params = new HashMap<>();

        params.put("key", MEI_RI_YING_YU_KEY);
        JSONObject ontSentence = Https.get(url, params, null);

        return ontSentence.getJSONObject("result").getString("content");
    }
// =================================================================================================

    private ThirdPartyApiServiceImp() {}

    @PostConstruct
    public void init() {
        WEATHER_KEY = weatherKey;
        MEI_RI_YI_YAN_KEY = meiRiYiYanKey;
        MEI_RI_YING_YU_KEY = meiRiYingYuKey;
    }

    @Value("${weather.key:default_value}")
    private String weatherKey;

    @Value("${yiyan.key.two:default_value}")
    private String meiRiYiYanKey;

    @Value("${yiyan.key.four:default_value}")
    private String meiRiYingYuKey;
}
