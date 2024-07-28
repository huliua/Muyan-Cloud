package com.muyan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-27 13:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeShareInfoDto implements Serializable {

    private static final String SerialVersionUID = "1L";

    private Long id;

    private String operateType;

    private String password;
}
