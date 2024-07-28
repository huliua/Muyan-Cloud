package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private Long id;

    private Long infoId;

    private String tagCode;
}
