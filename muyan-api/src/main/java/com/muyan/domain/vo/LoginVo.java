package com.muyan.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-05-09 19:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo implements Serializable {

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private String sex;

    private String signature;

    /**
     * token名称
     */
    private String tokenName;

    /**
     * token值
     */
    private String tokenValue;

    /**
     * 权限
     */
    private List<String> permissions;

    /**
     * 角色
     */
    private List<String> roles;
}
