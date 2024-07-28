package com.muyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muyan.domain.entity.CodeShareTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-14 18:26
 */
public interface CodeShareTagMapper extends BaseMapper<CodeShareTag> {

    void insertBatchSomeColumn(@Param("list") List<CodeShareTag> list);
}
