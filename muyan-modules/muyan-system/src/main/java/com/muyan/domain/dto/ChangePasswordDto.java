package com.muyan.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-09-01 16:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto implements Serializable {
    private final Long SerialVersionUID = 1L;
    private Long id;

    @NotBlank(message = "原密码不能为空！")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空！")
    @Size(min = 6, max = 20, message = "密码长度为6-20位！")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空！")
    @Size(min = 6, max = 20, message = "密码长度为6-20位！")
    private String confirmPassword;
}
