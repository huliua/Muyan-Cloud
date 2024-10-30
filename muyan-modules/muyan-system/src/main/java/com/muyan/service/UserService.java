package com.muyan.service;

import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.ChangePasswordDto;
import com.muyan.domain.dto.LoginDto;
import com.muyan.domain.dto.RegisterDto;
import com.muyan.domain.dto.UserUpdateDto;
import com.muyan.domain.vo.LoginVo;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 21:26
 */
public interface UserService {
    ResponseResult<LoginDto> getUser(LoginDto userDto);

    ResponseResult<List<String>> getUserPermission(Long userId);

    ResponseResult<List<String>> getUserRole(Long userId);

    ResponseResult<LoginVo> updateUser(UserUpdateDto userUpdateDto);

    ResponseResult<String> changePassword(ChangePasswordDto changePasswordDto);

    ResponseResult<String> register(RegisterDto registerDto);
}
