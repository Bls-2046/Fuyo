package com.github.dto.tabletime;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class FetchTabletimeResponse {
    private int status;
    private String message;
    private List<Tabletime> tabletime;

    @Data
    @Accessors(chain = true)
    public static class Tabletime {
        private String keyID;
        private String clazz;
        private int x;
        private int y;
        private int beginDay;
        private int endDay;
        private String weekType;
        private String place;
        private int startWeek;
        private int finishWeek;
    }
}