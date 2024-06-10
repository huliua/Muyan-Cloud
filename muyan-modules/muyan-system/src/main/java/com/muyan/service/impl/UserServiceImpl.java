package com.muyan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;
import com.muyan.entity.User;
import com.muyan.mapper.RolePermissionMapper;
import com.muyan.mapper.UserMapper;
import com.muyan.mapper.UserRoleMapper;
import com.muyan.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 21:27
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public ResponseResult<LoginDto> getUser(LoginDto loginDto) {
        log.info("请求参数：{}", loginDto);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(loginDto.getId()), User::getId, loginDto.getId());
        queryWrapper.eq(StrUtil.isNotEmpty(loginDto.getUsername()), User::getUsername, loginDto.getUsername());
        queryWrapper.eq(StrUtil.isNotEmpty(loginDto.getPhone()), User::getPhone, loginDto.getPhone());
        queryWrapper.eq(StrUtil.isNotEmpty(loginDto.getEmail()), User::getEmail, loginDto.getEmail());
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            return ResponseResult.fail("用户不存在!");
        }
        loginDto = BeanUtil.copyProperties(user, LoginDto.class);
        return ResponseResult.success(loginDto);
    }

    @Override
    public ResponseResult<List<String>> getUserPermission(Long userId) {
        List<String> userPermissions = rolePermissionMapper.selectByUserId(userId);
        return ResponseResult.success(userPermissions);
    }

    @Override
    public ResponseResult<List<String>> getUserRole(Long userId) {
        List<String> userPermissions = userRoleMapper.selectByUserId(userId);
        return ResponseResult.success(userPermissions);
    }
}
