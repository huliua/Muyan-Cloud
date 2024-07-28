package com.muyan.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muyan.domain.entity.CodeShareInfo;
import com.muyan.domain.vo.CodeShareInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-12 20:20
 */
public interface CodeShareInfoMapper extends BaseMapper<CodeShareInfo> {

    Page<CodeShareInfoVo> getCodesListByUserId(Page<CodeShareInfo> page, @Param("ew") LambdaQueryWrapper<CodeShareInfo> queryWrapper, @Param("userId") Long userId);
}
