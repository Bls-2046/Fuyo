package com.github.backend.service.impl;

import com.github.backend.dto.api.FetchWeatherResponse;
import com.github.backend.dto.schedule.FetchScheduleRequest;
import com.github.backend.dto.schedule.FetchScheduleResponse;
import com.github.backend.dto.tabletime.FetchTabletimeRequest;
import com.github.backend.dto.tabletime.FetchTabletimeResponse;
import com.github.backend.dto.user.FetchUserBaseInformationRequest;
import com.github.backend.dto.user.FetchUserBaseInformationResponse;
import com.github.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchDataServiceImpl implements FetchDataService {
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final TabletimeService tabletimeService;
    private final ThirdPartyApiService thirdPartyApiService;

    @Autowired
    public FetchDataServiceImpl(ScheduleService scheduleService, UserService userService, TabletimeService tabletimeService, ThirdPartyApiService thirdPartyApiService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.tabletimeService = tabletimeService;
        this.thirdPartyApiService = thirdPartyApiService;
    }

    /**
     * 获得用户的基本信息
     * @param fetchUserBaseInformationRequest 用户名
     * @return UserInformationResponse.UserInformation
     */
    @Override
    public FetchUserBaseInformationResponse.UserInformation fetchUserBaseInformation(FetchUserBaseInformationRequest fetchUserBaseInformationRequest) {
        return userService.fetchUserBaseInformation(fetchUserBaseInformationRequest);
    }

    /**
     * 获取用户课表信息
     * @param fetchTabletimeRequest 用户名
     * @return List<TabletimeResponse.Tabletime>
     */
    @Override
    public List<FetchTabletimeResponse.Tabletime> fetchTabletime(FetchTabletimeRequest fetchTabletimeRequest) {
        return tabletimeService.fetchTabletime(fetchTabletimeRequest);
    }

    /**
     * 获取用户日程信息
     * @param fetchScheduleRequest 用户名
     * @return List<ScheduleResponse.Schedule>
     */
    @Override
    public List<FetchScheduleResponse.Schedule> fetchSchedule(FetchScheduleRequest fetchScheduleRequest) {
        return scheduleService.fetchSchedule(fetchScheduleRequest);
    }

    /**
     * 获取珠海香洲区天气
     * <a href="https://lbs.amap.com/api/webservice/guide/api/weatherinfo/#t1">天气 API 文档</a>
     */
    @Override
    public FetchWeatherResponse.Live fetchWeather() {
        return thirdPartyApiService.fetchWeather();
    }

    /**
     * 获得随机的一个句子
     * @return String
     */
    @Override
    public String fetchYiYan() {
        return thirdPartyApiService.fetchOneSentence();
    }
}
