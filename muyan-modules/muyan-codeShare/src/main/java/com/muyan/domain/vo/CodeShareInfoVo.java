package com.muyan.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-23 20:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeShareInfoVo implements Serializable {

    private static final Long SerialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String description;

    private String visibility;

    private String password;

    private String cover;

    private Integer hasStared;
}