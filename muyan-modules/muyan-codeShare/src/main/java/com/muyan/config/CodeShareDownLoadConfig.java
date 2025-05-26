package com.muyan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "codeshare.download")
public class CodeShareDownLoadConfig {

    private String path;
}
