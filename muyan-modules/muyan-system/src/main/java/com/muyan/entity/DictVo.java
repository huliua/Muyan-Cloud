package com.muyan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用字典数据
 * @author huliua
 * @version 1.0
 * @date 2024-07-29 20:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictVo implements Serializable {
    private String code;
    private String name;
}
