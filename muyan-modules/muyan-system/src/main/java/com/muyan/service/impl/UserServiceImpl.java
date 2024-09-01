package com.muyan.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.muyan.constants.CommonConstants;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.ChangePasswordDto;
import com.muyan.domain.dto.LoginDto;
import com.muyan.domain.dto.UserUpdateDto;
import com.muyan.domain.entity.User;
import com.muyan.domain.vo.LoginVo;
import com.muyan.mapper.RolePermissionMapper;
import com.muyan.mapper.UserMapper;
import com.muyan.mapper.UserRoleMapper;
import com.muyan.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseResult<LoginVo> updateUser(UserUpdateDto userUpdateDto) {
        // 先更新用户信息
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userUpdateDto.getId());
        // 有可能字段为空，需要更新为null
        updateWrapper.set(StrUtil.isEmpty(userUpdateDto.getSex()), "sex", null);
        updateWrapper.set(StrUtil.isEmpty(userUpdateDto.getEmail()), "email", null);
        updateWrapper.set(StrUtil.isEmpty(userUpdateDto.getSignature()), "signature", null);
        userMapper.update(BeanUtil.copyProperties(userUpdateDto, User.class), updateWrapper);

        // 再返回最新的用户信息
        LoginDto loginDto = new LoginDto();
        loginDto.setId(userUpdateDto.getId());
        ResponseResult<LoginDto> result = getUser(loginDto);
        if (result.getCode() != CommonConstants.SUCCESS_CODE) {
            throw new RuntimeException(result.getMsg());
        }
        return ResponseResult.success(BeanUtil.copyProperties(result.getData(), LoginVo.class));
    }

    @Override
    public ResponseResult<String> changePassword(ChangePasswordDto changePasswordDto) {
        // 先获取用户信息，比对密码是否正确
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", changePasswordDto.getId());
        User user = userMapper.selectOne(queryWrapper);
        boolean checkRes = BCrypt.checkpw(changePasswordDto.getOldPassword(), user.getPassword());
        if (!checkRes) {
            return ResponseResult.fail("原密码不正确！");
        }

        // 更新密码
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", BCrypt.hashpw(changePasswordDto.getNewPassword(), BCrypt.gensalt()));
        updateWrapper.eq("id", changePasswordDto.getId());
        userMapper.update(updateWrapper);
        return ResponseResult.success();
    }
}
