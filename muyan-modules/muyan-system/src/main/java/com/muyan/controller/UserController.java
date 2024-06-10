package com.muyan.controller;

import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;
import com.muyan.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 21:25
 */
@Slf4j
@RestController
@RequestMapping("/user")
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
}
