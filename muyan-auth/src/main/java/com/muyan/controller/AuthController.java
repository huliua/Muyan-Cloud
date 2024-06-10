package com.muyan.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.muyan.block.UserApiBlock;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;
import com.muyan.domain.vo.LoginVo;
import com.muyan.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-24 23:05
 */
@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "登录模块")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    @SentinelResource(value = "/auth/login", blockHandlerClass = UserApiBlock.class, blockHandler = "getUserBlockHandler")
    public ResponseResult<LoginVo> login(@Parameter(name = "登录信息") @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/logout")
    @Operation(summary = "登出接口")
    @SentinelResource(value = "/auth/logout", blockHandlerClass = UserApiBlock.class, blockHandler = "getUserBlockHandler")
    public ResponseResult<Object> logout() {
        StpUtil.logout();
        return ResponseResult.success();
    }

    @GetMapping("/getLoginStatus")
    @Operation(summary = "获取登录状态")
    public ResponseResult<String> getLoginStatus() {
        return ResponseResult.success(StpUtil.getTokenInfo().toString());
    }

    @PostMapping("/getUserInfo")
    @Operation(summary = "获取当前登录的用户信息")
    public ResponseResult<LoginVo> getUserInfo() {
        return authService.getUserInfo();
    }

    @PostMapping("/getRoleList")
    public ResponseResult<List<String>> getRoleList(@RequestBody Long userId) {
        return authService.getRoleList(userId);
    }

    @PostMapping("/getPermissionList")
    public ResponseResult<List<String>> getPermissionList(@RequestBody Long userId) {
        return authService.getPermissionList(userId);
    }
}

