package com.muyan.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 权限不足异常
 * @author huliua
 * @version 1.0
 * @date 2024-07-27 15:06
 */
@NoArgsConstructor
@Getter
public class ForbiddenException extends RuntimeException {
    private final Integer code = 403;
    private final String msg = "权限不足";

    public ForbiddenException(String msg) {
        super(msg);
    }

}
