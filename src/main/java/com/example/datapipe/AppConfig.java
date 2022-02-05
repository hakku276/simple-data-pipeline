package com.example.datapipe;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("app.data-source")
public class AppConfig {

    private String categoryUrl;
    private String documentUrl;

}
