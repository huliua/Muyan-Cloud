package com.muyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muyan.domain.entity.CodeShareFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:34
 */
public interface CodeShareFileMapper extends BaseMapper<CodeShareFile> {

    void insertBatchSomeColumn(@Param("list") List<CodeShareFile> list);
}
