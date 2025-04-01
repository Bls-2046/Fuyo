package com.github.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)  // 添加这个注解以支持链式调用
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
