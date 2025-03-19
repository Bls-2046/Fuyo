package com.github.backend.dto;

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
        private String begin;
        private String end;
        private String weekType;
    }
}
