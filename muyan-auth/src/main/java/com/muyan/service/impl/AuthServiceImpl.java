package com.muyan.service.impl;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.muyan.api.UserApi;
import com.muyan.constant.RedisConstants;
import com.muyan.constants.CommonConstants;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;
import com.muyan.domain.dto.RegisterDto;
import com.muyan.domain.vo.LoginVo;
import com.muyan.service.AuthService;
import com.muyan.utils.EncodeUtils;
import com.muyan.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private EncodeUtils encodeUtils;

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
        password = encodeUtils.decode(password);

        // 校验密码是否正确
        boolean checkRes = BCrypt.checkpw(password, userInDb.getPassword());
        if (!checkRes) {
            return ResponseResult.fail("账号或密码错误！");
        }
        // 登录
        if (StrUtil.equals(loginDto.getRememberMe(), CommonConstants.YES)) {
            // 记住我模式下，默认7天有效，在此期间冻结账号后会自动解冻
            long expireTime = 7 * 24 * 60 * 60;
            // 注意：token有效期为7天，activeTimeout为30分钟。即使7天(timeout)到了，30分钟(activeTimeout)之内仍在操作系统，token就不会过期
            StpUtil.login(userInDb.getId(), new SaLoginModel().setTimeout(expireTime).setIsLastingCookie(true));
            // redis中存储的标志位有效期也为7天，7天之内可以获取到标志位，就会重新计算activeTimeout
            redisUtil.set(RedisConstants.REMEMBER_ME_KEY_PREFIX + StpUtil.getTokenValue(), CommonConstants.YES, expireTime);
        } else {
            StpUtil.login(userInDb.getId());
        }

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

    @Override
    public ResponseResult<String> register(RegisterDto registerDto) {
        // 校验数据
        if (StrUtil.hasEmpty(registerDto.getUsername(), registerDto.getNickname(), registerDto.getPassword(), registerDto.getConfirmPassword(), registerDto.getPhone(), registerDto.getSex())) {
            return ResponseResult.fail("参数错误，请检查！");
        }

        // 验证密码和确认密码是否一致
        String password = registerDto.getPassword();
        String confirmPassword = registerDto.getConfirmPassword();
        password = encodeUtils.decode(password);
        confirmPassword = encodeUtils.decode(confirmPassword);
        if (!StrUtil.equals(password, confirmPassword)) {
            return ResponseResult.fail("两次输入的密码不一致！");
        }

        // 执行注册
        registerDto.setPassword(password);
        registerDto.setConfirmPassword(confirmPassword);
        return userApi.register(registerDto);
    }
}
