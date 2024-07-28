package com.muyan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:14
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.muyan.mapper")
@EnableTransactionManagement
public class CodeShareApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeShareApplication.class, args);
    }
}