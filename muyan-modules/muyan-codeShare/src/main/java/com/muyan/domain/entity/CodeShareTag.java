package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-14 18:27
 */
@Data
@TableName("t_code_share_tag")
@NoArgsConstructor
@AllArgsConstructor
public class CodeShareTag implements Serializable {

    @TableField(exist = false)
    private Long SerialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long infoId;

    private String tagCode;
}
