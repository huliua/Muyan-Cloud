package com.muyan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息
 * @author huliua
 * @version 1.0
 * @date 2024-04-25 21:16
 */
@TableName("t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private Long id;

    private String username;

    private String nickname;

    private String password;

    private String phone;

    private String email;

    private String status;

    private String sex;

    private String avatar;

    private String signature;

    private Integer deleted;
}
