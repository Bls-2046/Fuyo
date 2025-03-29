package com.github.backend.dto.user;

import lombok.Data;

@Data
public class FetchUserBaseInformationResponse {
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
        private String nickname;
    }
}
