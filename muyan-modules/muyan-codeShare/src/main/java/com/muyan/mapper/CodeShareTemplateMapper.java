package com.muyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muyan.domain.entity.CodeShareTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2025-04-18 16:18
 */
public interface CodeShareTemplateMapper extends BaseMapper<CodeShareTemplate> {

    void insertBatchSomeColumn(@Param("list") List<CodeShareTemplate> list);
}