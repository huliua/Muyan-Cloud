package com.muyan.service;

import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;
import com.muyan.domain.vo.LoginVo;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 22:09
 */
public interface AuthService {
    ResponseResult<LoginVo> login(LoginDto loginDto);

    ResponseResult<List<String>> getRoleList(Long userId);

    ResponseResult<List<String>> getPermissionList(Long userId);

    ResponseResult<LoginVo> getUserInfo();
}
