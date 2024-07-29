package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 代码分享文件实体类
 *
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:32
 */
@Data
@TableName("t_code_share_file")
@NoArgsConstructor
@AllArgsConstructor
public class CodeShareFile implements Serializable {

    private static final Long SerialVersionUID = 1L;

    @TableId
    private String id;

    private Long infoId;

    private String name;

    private String parentId;

    private String type;

    private String content;
}
