package com.muyan.domain.dto;

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
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
