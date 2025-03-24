package com.github.backend.dto.api;

import lombok.Data;

@Data
public class WeatherResponse {
    private int status;
    private String message;
    private Live lives;

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
