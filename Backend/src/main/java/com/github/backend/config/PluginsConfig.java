package com.github.backend.config;

import com.github.backend.utils.InitApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class PluginsConfig {

    @Bean
    public InitApplication initApplication() throws IOException {
        return new InitApplication();
    }
}
