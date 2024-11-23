package com.muyan.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import com.muyan.constant.RedisConstants;
import com.muyan.constants.CommonConstants;
import com.muyan.utils.RedisUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author click33
 */
@Configuration
@Slf4j
public class SaTokenConfigure {
    @Resource
    private RedisUtil redisUtil;

    // 注册 Sa-Token全局过滤器 
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")
                // 开放地址
                .addExclude("/favicon.ico")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/auth/login 用于开放登录
                    SaRouter
                            .match("/**")
                            .notMatch("/auth/login", "/auth/register")
                            .check(r -> {
                                try {
                                    StpUtil.checkLogin();
                                    StpUtil.checkActiveTimeout();
                                } catch (NotLoginException ex) {
                                    // 记住我模式下，7天免登录，需要自动解冻
                                    String isRememberMe = StrUtil.toString(redisUtil.get(RedisConstants.REMEMBER_ME_KEY_PREFIX + StpUtil.getTokenValue()));
                                    if (StrUtil.equals(isRememberMe, CommonConstants.YES)) {
                                        // 冻结,需要解冻
                                        StpUtil.updateLastActiveToNow();
                                    } else {
                                        throw ex;
                                    }
                                }
                            });

                    // 权限认证 -- 不同模块, 校验不同权限
                    SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
                    SaRouter.match("/menu/**", r -> StpUtil.checkPermission("menu"));
                    SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
                    SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> {
                    log.error("Sa-Token 权限认证异常", e);
                    if (e instanceof NotLoginException) {
                        return SaResult.get(HttpStatus.UNAUTHORIZED.value(), "无权限访问，请先登录！", null);
                    }
                    return SaResult.error(e.getMessage());
                });
    }
}
