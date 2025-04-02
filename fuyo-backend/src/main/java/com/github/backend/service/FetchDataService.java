package com.github.backend.service;

import com.github.dto.thirdPartyAPI.FetchWeatherResponse;
import com.github.dto.schedule.FetchScheduleRequest;
import com.github.dto.schedule.FetchScheduleResponse;
import com.github.dto.tabletime.FetchTabletimeRequest;
import com.github.dto.tabletime.FetchTabletimeResponse;
import com.github.dto.user.FetchUserBaseInformationRequest;
import com.github.dto.user.FetchUserBaseInformationResponse;

import java.util.List;

/**
 * 数据获取方法接口
 */
public interface FetchDataService {
// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ User /////////////////////////////////////////////////
    /**
     * 获得用户基本信息
     * @param fetchUserBaseInformationRequest 用户名
     * @return UserInformationResponse.UserInformation
     */
    FetchUserBaseInformationResponse.UserInformation fetchUserBaseInformation(FetchUserBaseInformationRequest fetchUserBaseInformationRequest);


// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Tabletime ////////////////////////////////////////////
    /**
     * 获取课表信息
     * @param fetchTabletimeRequest 用户信息
     * @return TabletimeResponse
     */
    List<FetchTabletimeResponse.Tabletime> fetchTabletime(FetchTabletimeRequest fetchTabletimeRequest);

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Schedule /////////////////////////////////////////////

    /**
     * 获取用户的日程安排信息
     * @param fetchScheduleRequest 用户信息
     * @return ScheduleResponse
     */
    List<FetchScheduleResponse.Schedule> fetchSchedule(FetchScheduleRequest fetchScheduleRequest);

    // WeChat


// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ API ///////////////////////////////////////////////

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