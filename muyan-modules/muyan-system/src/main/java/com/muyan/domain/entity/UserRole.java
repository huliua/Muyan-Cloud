package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户权限信息
 *
 * @author huliua
 * @version 1.0
 * @date 2024-04-28 22:12
 */
@TableName("t_user_role")
@Data
public class UserRole implements Serializable {
    private Long id;

    private Long userId;

    private Long roleId;
}
