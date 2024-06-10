package com.muyan.filter;

import cn.dev33.satoken.same.SaSameUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * feign拦截器, 在feign请求发出之前，加入一些操作 
 */
@ConditionalOnProperty(prefix = "muyan.security", name = "checkSameToken", havingValue = "true")
@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor {
    // 为 Feign 的 RCP调用 添加请求头Same-Token 
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("add header:SAME_TOKEN");
        requestTemplate.header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken());
    }
}
