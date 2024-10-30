package com.muyan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-10-21 22:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String username;

    private String nickname;

    private String password;

    private String confirmPassword;

    private String phone;

    private String email;

    private String sex;

    private String signature;

    private String avatar;
}
