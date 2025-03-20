package com.github.fuyo.model;

import com.github.fuyo.dto.*;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.utils.AESUtil;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LoginModel {
    public LoginModel() {}

    /**
     * 登录
     */
    public String LoginVerification(String username, String password) {
        String message;

        String loginUrl = "http://localhost:8080/api/user/login"; // 验证请求地址
        LoginRequest loginRequest = new LoginRequest(username, password); // 构建请求体
        try {
            LoginResponse loginResponse = Https.<LoginResponse>post(loginUrl, loginRequest, LoginResponse.class);
            message = loginResponse.getMessage();
            log.info(message);

            if (loginResponse.getStatus() == 200) {
                new Thread(() -> {
                    String url = "http://localhost:8080/api/user/information";
                    UserInformationRequest userInformationRequest = new UserInformationRequest();
                    userInformationRequest.setUsername(username);
                    try {
                        UserInformationResponse userInformationResponse = Https.<UserInformationResponse>post(url, userInformationRequest, UserInformationResponse.class);

                        // 使用同步块确保线程安全
                        synchronized (UserEntity.getUserInformation()) {
                            UserEntity.getUserInformation().setUsername(userInformationResponse.getData().get("username"));
                            UserEntity.getUserInformation().setEmail(userInformationResponse.getData().get("name"));
                            UserEntity.getUserInformation().setDepartment(userInformationResponse.getData().get("department"));
                            UserEntity.getUserInformation().setPhone(userInformationResponse.getData().get("email"));
                            UserEntity.getUserInformation().setPhone(userInformationResponse.getData().get("phone"));
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }).start();

                new Thread(() -> {
                    String url = "http://localhost:8080/api/user/tabletime";
                    TabletimeRequest tabletimeRequest = new TabletimeRequest();
                    tabletimeRequest.setUsername(username);

                    try {
                        TableTimeResponse tableTimeResponse = Https.<TableTimeResponse>post(url, tabletimeRequest, TableTimeResponse.class);

                        List<UserEntity.Tabletime> tabletime = tableTimeResponse.getTabletime().stream()
                                .map(responseTabletime -> new UserEntity.Tabletime(
                                        responseTabletime.getKeyID(),
                                        responseTabletime.getClazz(),
                                        responseTabletime.getX(),
                                        responseTabletime.getY(),
                                        responseTabletime.getBeginDay(),
                                        responseTabletime.getEndDay(),
                                        responseTabletime.getWeekType(),
                                        responseTabletime.getPlace(),
                                        responseTabletime.getStartWeek(),
                                        responseTabletime.getFinishWeek()
                                ))
                                .collect(Collectors.toList());

                        log.info(tabletime.toString());

                        // 使用同步块确保线程安全
                        synchronized (UserEntity.getUserInformation()) {
                            UserEntity.getUserInformation().setTabletime(tabletime);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }).start();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return "登录异常";
        }
        return message;
    }

    // 用户名密码存放位置
    private static final String DATA_DIR = System.getenv("LOCALAPPDATA") + "\\Fuyo";
    private static final String LOGIN_DATA_FILE = DATA_DIR + "\\credentials.bin";

    // 确保目录和文件存在
    private void ensureFileExists() throws Exception {
        Path dirPath = Paths.get(DATA_DIR);
        Path filePath = Paths.get(LOGIN_DATA_FILE);

        // 如果目录不存在，则创建目录
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // 如果文件不存在，则创建文件
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    // 查找登录文件是否存在
    public boolean isExistLoginFile() throws Exception {
        Path loginFile = Paths.get(LOGIN_DATA_FILE);

        // 确保文件存在
        ensureFileExists();

        // 检查文件是否为空
        return Files.exists(loginFile) && Files.size(loginFile) > 0;
    }

    // 保存加密后的用户名和密码
    public void saveCredentials(String username, String password) throws Exception {
        // 将用户名和密码拼接为一行
        String data = username + ":" + password;

        // 加密数据
        String encryptedData = AESUtil.encrypt(data);

        // 将加密后的数据存储到二进制文件
        Files.write(Paths.get(LOGIN_DATA_FILE), encryptedData.getBytes());
    }

    // 读取并解密用户名和密码
    public String[] readCredentials() throws Exception {
        // 从二进制文件读取加密数据
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(LOGIN_DATA_FILE));
        String encryptedData = new String(encryptedBytes);

        // 解密数据
        String decryptedData = AESUtil.decrypt(encryptedData);

        // 解析用户名和密码
        return decryptedData.split(":");
    }

    // 清空二进制文件的内容
    public void clearFileContent() throws Exception {
        Path filePath = Paths.get(LOGIN_DATA_FILE);

        // 确保文件存在
        ensureFileExists();

        // 将文件内容截断为 0 字节
        Files.write(filePath, new byte[0]);
    }
}
