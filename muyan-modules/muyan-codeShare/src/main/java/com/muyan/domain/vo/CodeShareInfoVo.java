package com.muyan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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

    @Schema(description = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

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

    @Schema(description = "是否代码模板")
    private String isTemplate;

    @Schema(description = "是否点赞")
    private Integer hasStared;

    @Schema(description = "创建人用户名")
    private String username;

    @Schema(description = "创建人昵称")
    private String nickname;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}