package com.muyan.api;

import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;
import com.muyan.fallback.UserApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 21:53
 */
@FeignClient(value="muyan-system", fallbackFactory = UserApiFallback.class)
public interface UserApi {

    @PostMapping("/user/getUser")
    public ResponseResult<LoginDto> getUser(@RequestBody LoginDto loginDto);

    @PostMapping("/user/getUserPermission")
    public ResponseResult<List<String>> getUserPermission(@RequestBody Long userId);

    @PostMapping("/user/getUserRole")
    public ResponseResult<List<String>> getUserRole(@RequestBody Long userId);
}
