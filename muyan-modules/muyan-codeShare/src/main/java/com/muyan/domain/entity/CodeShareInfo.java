package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @TableField()
    private Long userId;

    private String title;

    private String description;

    private String visibility;

    private String password;

    private String cover;

    private Date createTime;

    private Date updateTime;

}
