package com.github.backend.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class InitApplication {
    public InitApplication() throws IOException {
        initApplication();
    }

    private static void initApplication() throws IOException {
//        // 初始化文件
//        globalTheme();
//        // 预加载脚本
        PythonScript.startPythonProcess("Backend/.venv/Scripts/python.exe", "Backend/src/main/resources/script/login_bitzh.py");
        log.info("InitApplicationClass@initApplication");
    }
}
