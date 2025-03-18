package com.github.backend.dto;

import lombok.Data;

@Data
public class UserInfoResponse {
    private int status;
    private String message;
    private UserInfo data;

    @Data
    public static class UserInfo {
        private String id;
        private String username;
        private String name;
        private String department;
        private String email;
        private String phone;
    }
}
