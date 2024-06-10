package com.muyan.service;

import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;

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
}
