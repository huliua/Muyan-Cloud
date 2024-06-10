package com.muyan.domain;

import lombok.Data;

import java.util.List;

/**
 * 分页查询结果
 * @author huliua
 * @version 1.0
 * @date 2024-05-05 17:00
 */
@Data
public class PageResult<T> {

    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 总分页数
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 分页查询结果
     */
    private List<T> rows;
}
