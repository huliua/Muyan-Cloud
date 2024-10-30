package com.muyan.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.ChangePasswordDto;
import com.muyan.domain.dto.LoginDto;
import com.muyan.domain.dto.RegisterDto;
import com.muyan.domain.dto.UserUpdateDto;
import com.muyan.domain.vo.LoginVo;
import com.muyan.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 21:25
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户模块")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/getUser")
    public ResponseResult<LoginDto> getUser(@RequestBody LoginDto userDto) {
        return userService.getUser(userDto);
    }

    @PostMapping("/getUserPermission")
    public ResponseResult<List<String>> getUserPermission(@RequestBody Long userId) {
        return userService.getUserPermission(userId);
    }

    @PostMapping("/getUserRole")
    public ResponseResult<List<String>> getUserRole(@RequestBody Long userId) {
        return userService.getUserRole(userId);
    }

    @PostMapping("/update")
    public ResponseResult<LoginVo> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        if (!StpUtil.hasRole("admin") || Objects.isNull(userUpdateDto.getId())) {
            userUpdateDto.setId(StpUtil.getLoginIdAsLong());
        }
        return userService.updateUser(userUpdateDto);
    }

    @PostMapping("/changePassword")
    public ResponseResult<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        if (!StpUtil.hasRole("admin") || Objects.isNull(changePasswordDto.getId())) {
            changePasswordDto.setId(StpUtil.getLoginIdAsLong());
        }
        return userService.changePassword(changePasswordDto);
    }

    @PostMapping("/register.do")
    @Operation(summary = "注册用户信息")
    public ResponseResult<String> register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }
}
