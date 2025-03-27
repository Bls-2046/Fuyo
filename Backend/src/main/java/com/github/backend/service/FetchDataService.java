package com.github.backend.service;

import com.github.backend.dto.api.FetchWeatherResponse;
import com.github.backend.dto.schedule.FetchScheduleRequest;
import com.github.backend.dto.schedule.FetchScheduleResponse;
import com.github.backend.dto.tabletime.FetchTabletimeRequest;
import com.github.backend.dto.tabletime.FetchTabletimeResponse;
import com.github.backend.dto.user.FetchUserBaseInformationRequest;
import com.github.backend.dto.user.FetchUserBaseInformationResponse;

import java.util.List;

/**
 * 数据获取方法接口
 */
public interface FetchDataService {
    /**
     * 获得用户基本信息
     * @param username 用户名
     * @return UserInformationResponse.UserInformation
     */
    FetchUserBaseInformationResponse.UserInformation fetchUserBaseInformation(FetchUserBaseInformationRequest fetchUserBaseInformationRequest);

    /**
     * 获取课表信息
     * @param fetchTabletimeRequest 用户信息
     * @return TabletimeResponse
     */
    List<FetchTabletimeResponse.Tabletime> fetchTabletime(FetchTabletimeRequest fetchTabletimeRequest);

    /**
     * 获取用户的日程安排信息
     * @param fetchScheduleRequest 用户信息
     * @return ScheduleResponse
     */
    List<FetchScheduleResponse.Schedule> fetchSchedule(FetchScheduleRequest fetchScheduleRequest);

    /**
     * 获取珠海香洲区天气
     * <a href="https://lbs.amap.com/api/webservice/guide/api/weatherinfo/#t1">天气 API 文档</a>
     */
    FetchWeatherResponse.Live fetchWeather();

    /**
     * 随机获得一个句子
     * @return String
     */
    String fetchYiYan();
}