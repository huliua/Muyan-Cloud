package com.muyan.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
@Schema(description = "代码信息VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeShareInfoVo implements Serializable {

    private static final Long SerialVersionUID = 1L;

    @Schema(description = "代码信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "可见性")
    private String visibility;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "是否点赞")
    private Integer hasStared;
}