package com.muyan.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.muyan.domain.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 * @author huliua
 * @version 1.0
 * @date 2024-05-09 21:38
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler()
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<String> handleException(Exception e) {
        log.error("系统异常:{}", e.getMessage());
        if (e instanceof NotLoginException) {
            return ResponseResult.fail(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
        return ResponseResult.fail(e.getMessage());
    }
}
