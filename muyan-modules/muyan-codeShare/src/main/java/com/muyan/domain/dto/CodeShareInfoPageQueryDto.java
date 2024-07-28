package com.muyan.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.muyan.domain.BaseQueryBean;
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
public class CodeShareInfoPageQueryDto extends BaseQueryBean {

    private String title;

    private String description;

    private String cover;

    private String queryType;

    private List<String> tag;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private List<Date> createTime;

}
