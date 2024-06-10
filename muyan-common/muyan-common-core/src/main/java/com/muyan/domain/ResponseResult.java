package com.muyan.domain;

import com.muyan.constants.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果封装
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 15:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(CommonConstants.SUCCESS_CODE, "success", null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(CommonConstants.SUCCESS_CODE, "success", data);
    }

    public static <T> ResponseResult<T> fail(Integer code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }

    public static <T> ResponseResult<T> fail(String msg) {
        return new ResponseResult<>(CommonConstants.FAIL_CODE, msg, null);
    }


    /**
     * 分页查询成功返回值
     * @param pageResult 分页查询结果
     * @param <T> 数据泛型
     */
    public static <T> ResponseResult<PageResult<T>> success(PageResult<T> pageResult) {
        return new ResponseResult<>(CommonConstants.SUCCESS_CODE, "success", pageResult);
    }

}
