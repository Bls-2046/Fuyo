package com.github.backend.config;

import com.github.backend.utils.PythonScript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PluginsConfig {

    public PluginsConfig() {
        loadingScript();
    }

    @Bean
    public PluginsConfig initApplication() {
        return new PluginsConfig();
    }

    private static void loadingScript() {
        // 预加载脚本
        PythonScript.startPythonProcess("Backend/.venv/Scripts/python.exe", "Backend/src/main/resources/script/login_bitzh.py");
    }
}
