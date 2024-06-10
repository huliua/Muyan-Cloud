package com.muyan.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.muyan.api.AuthApi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 当前用户的权限
 *
 * @author huliua
 * @version 1.0
 * @date 2024-04-28 21:44
 */
@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private AuthApi authApi;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        log.info("loginId:{}, loginType:{}", loginId, loginType);
        CompletableFuture<List<String>> completableFuture = CompletableFuture.supplyAsync(() -> authApi.getPermissionList(Long.valueOf(loginId.toString())).getData());
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        log.info("loginId:{}, loginType:{}", loginId, loginType);
        CompletableFuture<List<String>> completableFuture = CompletableFuture.supplyAsync(() -> authApi.getRoleList(Long.valueOf(loginId.toString())).getData());
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
