package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代码模板
 * @author huliua
 * @version 1.0
 * @date 2025-04-18 16:08
 */
@Data
@TableName("t_code_share_template")
@AllArgsConstructor
@NoArgsConstructor
public class CodeShareTemplate {

    @TableId
    private Long id;

    private Long infoId;

    private String name;

    private String type;

    private String description;

    private String required;

    private Integer sort;
}
