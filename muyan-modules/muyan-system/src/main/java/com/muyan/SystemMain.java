package com.muyan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 20:53
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.muyan.mapper")
public class SystemMain {
    public static void main(String[] args) {
        SpringApplication.run(SystemMain.class, args);
    }
}