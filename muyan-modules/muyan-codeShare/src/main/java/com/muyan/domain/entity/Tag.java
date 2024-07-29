package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-14 18:41
 */
@Data
@TableName("t_tag")
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {
    private static final Long SerialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String tagCode;

    private String tagName;
}
