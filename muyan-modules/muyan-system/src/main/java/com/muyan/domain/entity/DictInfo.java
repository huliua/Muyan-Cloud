package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-29 20:54
 */
@TableName("t_dict_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictInfo implements Serializable {

    private Long id;

    private String dictCode;

    private String tableName;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
