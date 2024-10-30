package com.muyan.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户权限信息
 *
 * @author huliua
 * @version 1.0
 * @date 2024-04-28 22:13
 */
@TableName("t_role_permission")
@Data
public class RolePermission implements Serializable {

    private Long id;

    private Long roleId;

    private Long permissionId;
}
