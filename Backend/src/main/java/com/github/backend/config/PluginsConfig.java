package com.github.backend.config;

import com.github.backend.utils.PythonScript;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PluginsConfig {
    // 预加载脚本
    @PostConstruct
    private void loadingScript() {
        PythonScript.startPythonProcess(
                "Backend/.venv/Scripts/python.exe",
                "Backend/src/main/resources/script/login_bitzh.py"
        );
    }
}
