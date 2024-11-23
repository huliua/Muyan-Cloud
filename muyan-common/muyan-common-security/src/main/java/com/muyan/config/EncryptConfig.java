package com.muyan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 加密配置类
 * @author huliua
 * @version 1.0
 * @date 2024-10-30 15:11
 */

@Component
@ConfigurationProperties(prefix = "auth.encrypt")
@Data
public class EncryptConfig {
    private String privateKey;
}
