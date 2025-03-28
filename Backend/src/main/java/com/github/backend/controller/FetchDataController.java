package com.github.backend.controller;

import com.github.backend.dto.thirdPartyAPI.FetchYiYanResponse;
import com.github.backend.dto.thirdPartyAPI.FetchWeatherResponse;
import com.github.backend.dto.schedule.FetchScheduleRequest;
import com.github.backend.dto.schedule.FetchScheduleResponse;
import com.github.backend.dto.tabletime.FetchTabletimeRequest;
import com.github.backend.dto.tabletime.FetchTabletimeResponse;
import com.github.backend.dto.user.FetchUserBaseInformationRequest;
import com.github.backend.dto.user.FetchUserBaseInformationResponse;
import com.github.backend.service.FetchDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/fetch")
public class FetchDataController {
    private final FetchDataService fetchDataService;

    @Autowired
    public FetchDataController(FetchDataService fetchDataService) {
        this.fetchDataService = fetchDataService;
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ User ///////////////////////////////////////////////
    /**
     * 获取用户基本信息
     * @param fetchUserBaseInformationRequest 查询条件
     * @return UserInformationResponse
     */
    @PostMapping("/user-base-information")
    public FetchUserBaseInformationResponse fetchUserBaseInformation(@RequestBody FetchUserBaseInformationRequest fetchUserBaseInformationRequest) {

        FetchUserBaseInformationResponse fetchUserBaseInformationResponse = new FetchUserBaseInformationResponse();

        try {
            fetchUserBaseInformationResponse.setData(fetchDataService.fetchUserBaseInformation(fetchUserBaseInformationRequest));

            if (fetchUserBaseInformationResponse.getData() != null) {
                fetchUserBaseInformationResponse.setStatus(200);
                fetchUserBaseInformationResponse.setMessage("get information successful");
            } else {
                fetchUserBaseInformationResponse.setStatus(204);
                fetchUserBaseInformationResponse.setMessage("get information failed");
            }
        } catch (Exception e) {
            fetchUserBaseInformationResponse.setStatus(500);
            fetchUserBaseInformationResponse.setMessage(e.getMessage());
        }
        return fetchUserBaseInformationResponse;
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Tabletime ////////////////////////////////////////////
    /**
     * 获取课表信息
     * @param fetchTabletimeRequest 用户信息
     * @return TabletimeResponse
     */
    @PostMapping("/tabletime")
    public FetchTabletimeResponse fetchTabletime(@RequestBody FetchTabletimeRequest fetchTabletimeRequest) {

        FetchTabletimeResponse fetchTabletimeResponse = new FetchTabletimeResponse();

        try {
            fetchTabletimeResponse.setTabletime(fetchDataService.fetchTabletime(fetchTabletimeRequest));

            fetchTabletimeResponse.setStatus(200);
            fetchTabletimeResponse.setMessage("get tabletime successful");

        } catch (Exception e) {
            fetchTabletimeResponse.setStatus(500);
            fetchTabletimeResponse.setMessage(e.getMessage());
        }
        return fetchTabletimeResponse;
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Schedule /////////////////////////////////////////////
    /**
     * 获取用户的日程安排信息
     * @param fetchScheduleRequest 用户信息
     * @return ScheduleResponse
     */
    @PostMapping("/schedule")
    public FetchScheduleResponse fetchSchedule(@RequestBody FetchScheduleRequest fetchScheduleRequest) {

        FetchScheduleResponse fetchScheduleResponse = new FetchScheduleResponse();

        try {
            fetchScheduleResponse.setSchedule(fetchDataService.fetchSchedule(fetchScheduleRequest));

            fetchScheduleResponse.setStatus(200);
            fetchScheduleResponse.setMessage("get schedule successful");

        } catch(Exception e) {
            fetchScheduleResponse.setStatus(500);
            fetchScheduleResponse.setMessage(e.getMessage());
        }
        return fetchScheduleResponse;
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Third Party API //////////////////////////////////////////
    /**
     * 获得珠海香洲区天气
     * @return WeatherResponse
     */
    @GetMapping("/weather")
    public FetchWeatherResponse fetchWeather() {
        FetchWeatherResponse fetchWeatherResponse = new FetchWeatherResponse();

        FetchWeatherResponse.Live weatherLiveResponse = fetchDataService.fetchWeather();

        if (weatherLiveResponse != null) {
            fetchWeatherResponse.setStatus(200);
            fetchWeatherResponse.setLive(weatherLiveResponse);
        } else {
            fetchWeatherResponse.setStatus(404);
        }
        return fetchWeatherResponse;
    }

    /**
     * 获得一句佳句
     * @return String
     */
    @GetMapping("/yiyan")
    public FetchYiYanResponse fetchYiYan() {
        String oneSentence;
        FetchYiYanResponse fetchYiYanResponse = new FetchYiYanResponse();

        oneSentence = fetchDataService.fetchYiYan();

        if (oneSentence != null) {
            fetchYiYanResponse.setStatus(200);
            fetchYiYanResponse.setSentence(oneSentence);
        } else {
            fetchYiYanResponse.setStatus(404);
        }
        return fetchYiYanResponse;
    }
}
