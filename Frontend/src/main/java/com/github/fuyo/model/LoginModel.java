package com.github.fuyo.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fuyo.dto.*;
import com.github.fuyo.entity.UserEntity;
import com.github.fuyo.listener.StartupTasks;
import com.github.fuyo.utils.AESUtil;
import com.github.fuyo.utils.https.Https;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class LoginModel {
    public LoginModel() {}

    /**
     * 登录
     */
    public String loginVerification(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            log.warn("登录验证: 用户名或密码为空");
            return "用户名或密码不能为空";
        }

        String loginUrl = "http://localhost:8080/api/operation/login";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        log.warn("登录请求: {}", loginRequest);

        try {
            LoginResponse loginResponse = Https.post(loginUrl, loginRequest, null, LoginResponse.class);

            log.info("登录响应: {}", loginResponse.getStatus());

            if (loginResponse.getStatus() == HttpStatus.OK.value()) {
                // 执行异步任务
                executeAsyncTasks(username);
                StartupTasks.scheduler();
                return "登录成功";
            }
            return loginResponse.getMessage();
        } catch (IOException e) {
            // 特殊处理401错误（不修改Https类的方式）
            if (e.getMessage() != null && e.getMessage().startsWith("HTTP 401")) {
                try {
                    // 从异常消息中提取JSON部分
                    String jsonPart = e.getMessage().substring(e.getMessage().indexOf("{"));
                    LoginResponse errorResponse = new ObjectMapper()
                            .readValue(jsonPart, LoginResponse.class);
                    return errorResponse.getMessage(); // 返回"用户名错误"
                } catch (Exception jsonEx) {
                    return "用户名或密码错误";
                }
            }
            // 真正的网络错误
            log.error("网络请求失败", e);
            return "网络连接异常，请检查网络设置";
        } catch (Exception e) {
            log.error("系统异常", e);
            return "系统繁忙，请稍后再试";
        }
    }

    /**
     * 执行异步任务
     */
    private void executeAsyncTasks(String username) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        // 任务1: 获取用户信息
        new Thread(() -> {
            log.warn("开始获取用户信息...");
            try {
                UserInformationModel.getUserInformation(username);
            } catch (Exception e) {
                log.error("获取用户信息失败: {}", e.getMessage());
            } finally {
                latch.countDown();
                log.info("成功获取用户信息");
            }
        }).start();

        // 任务2: 获取用户课表信息
        new Thread(() -> {
            log.warn("开始获取用户课表信息...");
            try {
                TabletimeModel.fetchTabletime(username);
            } catch (Exception e) {
                log.error("获取课表失败: {}", e.getMessage());
            } finally {
                latch.countDown();
                log.info("成功获取用户课表信息");
            }
        }).start();

        // 任务3: 用户日程信息
        new Thread(() -> {
            log.warn("开始获取用户日程信息...");
            try {
                ScheduleModel.fetchSchedule(username);
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                latch.countDown();
                log.info("成功获取用户日程信息");
            }
        }).start();

        latch.await();
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
