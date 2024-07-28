package com.muyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muyan.domain.entity.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-06-14 18:41
 */
public interface TagMapper extends BaseMapper<Tag> {
    public void insertNotExists(@Param("tagList") List<Tag> tagList);
}
