package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * codeShare实体类
 *
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:22
 */
@Data
@TableName("t_code_share_info")
@NoArgsConstructor
@AllArgsConstructor
public class CodeShareInfo implements Serializable {

    @TableField(exist = false)
    private Long SerialVersionUID = 1L;

    @TableId
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    private String title;

    private String description;

    private String visibility;

    private String password;

    private String cover;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
