package com.github.fuyo.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserViewModel {
    public UserViewModel() {}

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

    // 清空二进制文件的内容
    public void clearFileContent() throws Exception {
        Path filePath = Paths.get(LOGIN_DATA_FILE);

        // 确保文件存在
        ensureFileExists();

        // 将文件内容截断为 0 字节
        Files.write(filePath, new byte[0]);
    }
}
