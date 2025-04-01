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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param fetchUserBaseInformationRequest 请求体
     * @return FetchUserBaseInformationResponse
     */
    @PostMapping("/user-base-information")
    public ResponseEntity<FetchUserBaseInformationResponse> fetchUserBaseInformation(
            @RequestBody FetchUserBaseInformationRequest fetchUserBaseInformationRequest) {

        FetchUserBaseInformationResponse response = new FetchUserBaseInformationResponse();

        try {
            FetchUserBaseInformationResponse.UserInformation userInfo =
                    fetchDataService.fetchUserBaseInformation(fetchUserBaseInformationRequest);

            if (userInfo != null) {
                response.setStatus(HttpStatus.OK.value())
                        .setMessage("获取信息成功")
                        .setData(userInfo);

                log.info(String.valueOf(response));
                return ResponseEntity.ok(response);
            } else {
                // 业务预期内的失败（数据不存在）
                response.setStatus(HttpStatus.NOT_FOUND.value())
                        .setMessage("未找到用户信息");

                log.error(String.valueOf(response));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (RuntimeException e) {
            // 已知的业务异常（如权限不足）
            response.setStatus(HttpStatus.FORBIDDEN.value())
                    .setMessage(e.getMessage());

            log.error(String.valueOf(response));
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception e) {
            // 系统异常
            log.error("获取用户信息异常: username={}", fetchUserBaseInformationRequest.getUsername(), e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("系统繁忙，请稍后重试");

            log.error(String.valueOf(response));
            return ResponseEntity.internalServerError().body(response);
        }
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Tabletime ////////////////////////////////////////////

    /**
     * 获取课表信息
     * @param fetchTabletimeRequest 包含用户名的请求体
     * @return ResponseEntity<FetchTabletimeResponse>
     */
    @PostMapping("/tabletime")
    public ResponseEntity<FetchTabletimeResponse> fetchTabletime(
            @RequestBody FetchTabletimeRequest fetchTabletimeRequest) {

        FetchTabletimeResponse response = new FetchTabletimeResponse();
        String username = fetchTabletimeRequest.getUsername();

        try {
            List<FetchTabletimeResponse.Tabletime> tabletimeData =
                    fetchDataService.fetchTabletime(fetchTabletimeRequest);

            if (tabletimeData != null && !tabletimeData.isEmpty()) {
                response.setStatus(HttpStatus.OK.value())
                        .setMessage("获取课表成功")
                        .setTabletime(tabletimeData);

                log.info("课表获取成功: {}", response);
                return ResponseEntity.ok(response);
            } else {
                // 无课表数据
                response.setStatus(HttpStatus.NOT_FOUND.value())
                        .setMessage("该用户暂无课表信息");

                log.error("无课表数据: {}", response);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value())
                    .setMessage(e.getMessage());

            log.error("课表查询业务异常: username={}, response={}", username, response);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception e) {
            // 系统异常
            log.error("课表查询系统异常: username={}", username, e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("系统繁忙，请稍后重试");

            log.error("系统异常响应: {}", response);
            return ResponseEntity.internalServerError().body(response);
        }
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Schedule /////////////////////////////////////////////

    /**
     * 获取用户的日程安排信息
     * @param fetchScheduleRequest 用户信息
     * @return ResponseEntity<FetchScheduleResponse>
     */
    @PostMapping("/schedule")
    public ResponseEntity<FetchScheduleResponse> fetchSchedule(
            @RequestBody FetchScheduleRequest fetchScheduleRequest) {

        FetchScheduleResponse response = new FetchScheduleResponse();
        String username = fetchScheduleRequest.getUsername();

        try {
            // 参数校验
            if (username == null || username.trim().isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value())
                        .setMessage("用户名不能为空");

                log.error("参数校验失败: {}", response);
                return ResponseEntity.badRequest().body(response);
            }

            // 业务逻辑
            List<FetchScheduleResponse.Schedule> schedules =
                    fetchDataService.fetchSchedule(fetchScheduleRequest);

            if (schedules != null && !schedules.isEmpty()) {
                response.setStatus(HttpStatus.OK.value())
                        .setMessage("获取日程成功")
                        .setSchedule(schedules);

                log.info("日程获取成功: username={}", username);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value())
                        .setMessage("当前无日程安排");

                log.warn("空日程数据: username={}", username);
                log.info(String.valueOf(response));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value())
                    .setMessage(e.getMessage());


            log.error("业务异常: username={}, error={}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception e) {
            // 系统异常
            log.error("获取日程系统异常: username={}", username, e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("系统繁忙，请稍后重试");

            return ResponseEntity.internalServerError().body(response);
        }
    }

// =================================================================================================
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ Third Party API //////////////////////////////////////////

    /**
     * 获得珠海香洲区天气
     * @return ResponseEntity<FetchWeatherResponse>
     */
    @GetMapping("/weather")
    public ResponseEntity<FetchWeatherResponse> fetchWeather() {

        FetchWeatherResponse response = new FetchWeatherResponse();

        try {
            FetchWeatherResponse.Live weatherData = fetchDataService.fetchWeather();

            if (weatherData != null) {
                response.setStatus(HttpStatus.OK.value())
                        .setMessage("天气数据获取成功")
                        .setLive(weatherData);

                log.info("天气数据获取成功: {}", response);
                return ResponseEntity.ok(response);
            } else {
                // 无天气数据
                response.setStatus(HttpStatus.NOT_FOUND.value())
                        .setMessage("暂未获取到天气数据");

                log.error("天气数据为空: {}", response);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (RuntimeException e) {
            // 已知的业务异常
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value())
                    .setMessage(e.getMessage());

            log.error("天气接口业务异常: {}", response);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        } catch (Exception e) {
            // 系统异常
            log.error("获取天气系统异常", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("天气服务暂不可用，请稍后重试");

            log.error("系统异常响应: {}", response);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 获得一句佳句
     * @return ResponseEntity<FetchYiYanResponse>
     */
    @GetMapping("/yiyan")
    public ResponseEntity<FetchYiYanResponse> fetchYiYan() {

        FetchYiYanResponse response = new FetchYiYanResponse();

        try {
            String sentence = fetchDataService.fetchYiYan();

            if (sentence != null && !sentence.isEmpty()) {
                response.setStatus(HttpStatus.OK.value())
                        .setMessage("佳句获取成功")
                        .setSentence(sentence);

                log.info("佳句获取成功: {}", response);
                return ResponseEntity.ok(response);
            } else {
                // 业务预期内的失败（无佳句数据）
                response.setStatus(HttpStatus.NOT_FOUND.value())
                        .setMessage("暂未获取到佳句");

                log.error("佳句数据为空: {}", response);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (RuntimeException e) {
            // 已知的业务异常（如API限流）
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value())
                    .setMessage(e.getMessage());

            log.error("佳句接口业务异常: {}", response);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        } catch (Exception e) {
            // 系统异常
            log.error("获取佳句系统异常", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("服务暂不可用，请稍后重试");

            log.error("系统异常响应: {}", response);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
