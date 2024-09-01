package com.muyan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-09-01 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto implements Serializable {

    private final Long SerialVersionUID = 1L;

    private Long id;

    private String nickname;

    private String phone;

    private String email;

    private String sex;

    private String avatar;

    private String signature;
}
