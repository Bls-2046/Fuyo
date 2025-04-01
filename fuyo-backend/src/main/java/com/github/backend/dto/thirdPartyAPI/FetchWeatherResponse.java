package com.github.backend.dto.thirdPartyAPI;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FetchWeatherResponse {
    private int status;
    private String message;
    private Live live;

    @Data
    @Accessors(chain = true)
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
