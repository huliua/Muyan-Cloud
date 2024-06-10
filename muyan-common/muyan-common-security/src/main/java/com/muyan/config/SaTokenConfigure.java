package com.muyan.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 权限认证 配置类
 */
@ConditionalOnProperty(prefix = "muyan.security", name = "checkSameToken", havingValue = "true")
@Configuration
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册 Sa-Token 全局过滤器 
    @Bean
    public SaServletFilter getSaServletFilter() {
        log.info("check header:SAME_TOKEN");
        return new SaServletFilter()
                .addInclude("/**")
                .addExclude("/favicon.ico")
                .setAuth(obj -> {
                    // 校验 Same-Token 身份凭证
                    String token = SaHolder.getRequest().getHeader(SaSameUtil.SAME_TOKEN);
                    SaSameUtil.checkToken(token);
                })
                .setError(e -> {
                    log.error("Sa-Token 认证拦截器发生异常：", e);
                    return SaResult.error(e.getMessage());
                });
    }
}
