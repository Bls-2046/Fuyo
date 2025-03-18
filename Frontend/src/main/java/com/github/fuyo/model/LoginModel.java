package com.github.fuyo.model;

import com.github.fuyo.dto.LoginRequest;
import com.github.fuyo.dto.LoginResponse;
import com.github.fuyo.utils.https.Https;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class LoginModel {
    public LoginModel() {}

    /**
     * 登录
     */
    public String LoginVerification(String username, String password) {
        String message = null;

        String loginUrl = "http://localhost:8080/api/user/login"; // 验证请求地址
        LoginRequest loginRequest = new LoginRequest(username, password); // 构建请求体
        try {
            LoginResponse loginResponse = Https.<LoginResponse>post(loginUrl, loginRequest, LoginResponse.class);

            message = loginResponse.getMessage(); // 获得登录信息

            if (loginResponse.getStatus() == 200) {
                new Thread(() -> {
//                     UserRequest userResponse = new UserRequest(username);
//                    try {
//                        String getUserInfoUrl = "http://localhost:8080/api/user/info";
//                        UserResponse userResponse = Https.<UserResponse>post(getUserInfoUrl, UserRequest, UserResponse.class);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "登录异常";
        }
        return message;
    }

    // 用户名密码存放位置
    private static final String FILE_PATH =System.getenv("LOCALAPPDATA") + "\\Fuyo\\" + "loginData.txt";

    public boolean isExistLoginFile() throws IOException {
        Path loginFile = Paths.get(FILE_PATH);
        return Files.exists(loginFile) && Files.size(loginFile) > 0;
    } // 查找登录文件是否存在

    /**
     * 读取账号和密码
     */
    public String[] readCredentials() {
        String[] loginData = null;
        try {
            Path path = Paths.get(FILE_PATH);

            // 读取文件内容
            String content = new String(Files.readAllBytes(path));

            // 解析文件内容
            String username = content.split("\n")[0].split("=")[1];
            String encryptedPassword = content.split("\n")[1].split("=")[1];

            // 解密密码
            String password = decrypt(encryptedPassword);

            loginData = new String[]{username, password};

        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginData;
    }

    /**
     * 加密
     */
    private static final String ENCRYPTION_KEY = "mySecretKey12345"; // 加密密钥（需妥善保管）

    private String encrypt(String data) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密
     */
    private String decrypt(String encryptedData) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
}
