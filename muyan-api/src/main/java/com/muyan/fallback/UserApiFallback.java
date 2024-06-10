package com.muyan.fallback;

import com.muyan.api.UserApi;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户模块接口降级处理
 *
 * @author huliua
 * @version 1.0
 * @date 2024-05-05 18:49
 */
@Component
public class UserApiFallback implements FallbackFactory<UserApi> {
    @Override
    public UserApi create(Throwable cause) {
        return new UserApi() {

            @Override
            public ResponseResult<LoginDto> getUser(LoginDto loginDto) {
                return ResponseResult.fail(cause.getMessage());
            }

            @Override
            public ResponseResult<List<String>> getUserPermission(Long userId) {
                return ResponseResult.fail(cause.getMessage());
            }

            @Override
            public ResponseResult<List<String>> getUserRole(Long userId) {
                return ResponseResult.fail(cause.getMessage());
            }
        };
    }
}
