package com.github.backend.dto.thirdPartyAPI;

import lombok.Data;

@Data
public class FetchWeatherResponse {
    private int status;
    private Live live;

    @Data
    public static class Live {
        private String province;
        private String city;
        private String weather;
        private String temperature;
        private String winddirection;
        private String windpower;
        private String humidity;
        private String reporttime;
        private String temperature_float;
        private String humidity_float;
    }
}
