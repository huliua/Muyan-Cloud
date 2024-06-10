package com.muyan.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础查询类
 * @author huliua
 * @version 1.0
 * @date 2024-05-05 17:06
 */
@Data
public class BaseQueryBean implements Serializable {

    /**
     * 每页显示条数
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 排序字段
     * @example "+id,-operate_time" ==> order by id, operate_time desc
     */
    private String sort;
}
