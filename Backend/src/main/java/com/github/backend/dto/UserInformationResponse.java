package com.github.backend.dto;

import lombok.Data;

@Data
public class UserInformationResponse {
    private int status;
    private String message;
    private UserInformation data;

    @Data
    public static class UserInformation {
        private String username;
        private String name;
        private String department;
        private String email;
        private String phone;
    }
}
