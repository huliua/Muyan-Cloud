package com.muyan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 21:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable {

    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private String password;

    private String sex;

    private String signature;

    /**
     * 记住我
     */
    private String rememberMe;

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
