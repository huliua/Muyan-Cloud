package com.muyan.fallback;

import com.muyan.api.AuthApi;
import com.muyan.domain.ResponseResult;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限接口服务降级处理
 * @author huliua
 * @version 1.0
 * @date 2024-05-05 18:53
 */
@Component
public class AuthApiFallback implements FallbackFactory<AuthApi> {
    @Override
    public AuthApi create(Throwable cause) {
        return new AuthApi() {
            @Override
            public ResponseResult<List<String>> getPermissionList(Long userId) {
                return ResponseResult.fail(cause.getMessage());
            }

            @Override
            public ResponseResult<List<String>> getRoleList(Long userId) {
                return ResponseResult.fail(cause.getMessage());
            }
        };
    }
}
