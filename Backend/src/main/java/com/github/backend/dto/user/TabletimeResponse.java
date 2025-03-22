package com.github.backend.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class TabletimeResponse {
    private String status;
    private String message;
    private List<Tabletime> tabletime;

    @Data
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
