package com.muyan.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.muyan.api.UserApi;
import com.muyan.config.EncryptConfig;
import com.muyan.constant.RedisConstants;
import com.muyan.constants.CommonConstants;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;
import com.muyan.domain.vo.LoginVo;
import com.muyan.service.AuthService;
import com.muyan.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 22:10
 */
@Service
@SuppressWarnings("unchecked")
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserApi userApi;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private EncryptConfig encryptConfig;

    @Override
    public ResponseResult<LoginVo> login(LoginDto loginDto) {
        // 查询用户信息
        ResponseResult<LoginDto> result = userApi.getUser(loginDto);
        if (result.getCode() != CommonConstants.SUCCESS_CODE) {
            return ResponseResult.fail(result.getMsg());
        }
        LoginDto userInDb = result.getData();

        // 解密前端传递的密码
        String password = loginDto.getPassword();
        RSA rsa = new RSA(encryptConfig.getPrivateKey(), null);
        byte[] decrypt = rsa.decrypt(password, KeyType.PrivateKey);
        password = new String(decrypt, CharsetUtil.CHARSET_UTF_8);

        // 校验密码是否正确
        boolean checkRes = BCrypt.checkpw(password, userInDb.getPassword());
        if (!checkRes) {
            return ResponseResult.fail("账号或密码错误！");
        }
        // 登录
        StpUtil.login(userInDb.getId());

        // 获取token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        userInDb.setTokenName(tokenInfo.getTokenName());
        userInDb.setTokenValue(tokenInfo.getTokenValue());

        // 获取用户权限信息
        ResponseResult<List<String>> roleRes = this.getRoleList(userInDb.getId());
        if (roleRes.getCode() == CommonConstants.SUCCESS_CODE) {
            userInDb.setRoles(roleRes.getData());
        }
        ResponseResult<List<String>> permissionRes = this.getPermissionList(userInDb.getId());
        if (permissionRes.getCode() == CommonConstants.SUCCESS_CODE) {
            userInDb.setPermissions(permissionRes.getData());
        }
        return ResponseResult.success(BeanUtil.copyProperties(userInDb, LoginVo.class));
    }

    @Override
    public ResponseResult<List<String>> getRoleList(Long userId) {
        // 首先从redis中获取
        String strVal = (String) redisUtil.get(RedisConstants.ROLE_KEY_PREFIX + userId);
        List<String> roleList = (List<String>) JSON.parse(strVal);
        // redis中没有数据则请求
        if (CollectionUtil.isEmpty(roleList)) {
            ResponseResult<List<String>> result = userApi.getUserRole(userId);
            if (result.getCode() != CommonConstants.SUCCESS_CODE) {
                return result;
            }
            roleList = result.getData();
            // 存入redis
            redisUtil.set(RedisConstants.ROLE_KEY_PREFIX + userId, JSON.toJSONString(roleList), RedisConstants.EXPIRE_TIME);
        }
        return ResponseResult.success(roleList);
    }

    @Override
    public ResponseResult<List<String>> getPermissionList(Long userId) {
        // 首先从redis中获取
        String strVal = (String) redisUtil.get(RedisConstants.PERMISSION_KEY_PREFIX + userId);

        List<String> permissionList = (List<String>) JSON.parse(strVal);
        // redis中没有数据则请求
        if (CollectionUtil.isEmpty(permissionList)) {
            ResponseResult<List<String>> result = userApi.getUserPermission(userId);
            if (result.getCode() != CommonConstants.SUCCESS_CODE) {
                return result;
            }
            permissionList = result.getData();
            // 存入redis
            redisUtil.set(RedisConstants.PERMISSION_KEY_PREFIX + userId, JSON.toJSONString(permissionList), RedisConstants.EXPIRE_TIME);
        }
        return ResponseResult.success(permissionList);
    }

    @Override
    public ResponseResult<LoginVo> getUserInfo() {
        // 获取当前用户id
        long userId = StpUtil.getLoginIdAsLong();
        LoginDto user = new LoginDto();
        user.setId(userId);
        ResponseResult<LoginDto> result = userApi.getUser(user);
        if (result.getCode() != CommonConstants.SUCCESS_CODE) {
            return ResponseResult.fail(result.getMsg());
        }
        user = result.getData();

        // 获取用户权限信息
        ResponseResult<List<String>> roleRes = this.getRoleList(user.getId());
        if (roleRes.getCode() == CommonConstants.SUCCESS_CODE) {
            user.setRoles(roleRes.getData());
        }
        ResponseResult<List<String>> permissionRes = this.getPermissionList(user.getId());
        if (permissionRes.getCode() == CommonConstants.SUCCESS_CODE) {
            user.setPermissions(permissionRes.getData());
        }
        return ResponseResult.success(BeanUtil.copyProperties(user, LoginVo.class));

    }
}
