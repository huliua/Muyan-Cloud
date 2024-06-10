package com.muyan.api;

import com.muyan.domain.ResponseResult;
import com.muyan.fallback.AuthApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-28 21:50
 */
@FeignClient(value = "muyan-auth", fallbackFactory = AuthApiFallback.class)
public interface AuthApi {

    @PostMapping("/auth/getPermissionList")
    public ResponseResult<List<String>> getPermissionList(@RequestBody Long userId);

    @PostMapping("/auth/getRoleList")
    public ResponseResult<List<String>> getRoleList(@RequestBody Long userId);
}
