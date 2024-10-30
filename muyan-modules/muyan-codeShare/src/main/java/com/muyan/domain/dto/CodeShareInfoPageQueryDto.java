package com.muyan.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.muyan.domain.BaseQueryBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-23 20:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "分页查询代码信息Dto")
public class CodeShareInfoPageQueryDto extends BaseQueryBean {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "查询类型")
    private String queryType;

    @Schema(description = "标签")
    private List<String> tag;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private List<Date> createTime;

    @Schema(description = "代码内容")
    private String content;

}
