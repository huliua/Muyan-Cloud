package com.muyan.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.LoginDto;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-27 20:45
 */
public class UserApiBlock {

    public ResponseResult<LoginDto> getUserBlockHandler(LoginDto login, BlockException e) {
        return ResponseResult.fail("系统繁忙，请稍后再试~");
    }
}
