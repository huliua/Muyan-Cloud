package com.muyan.mapper;

import com.muyan.entity.DictVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-29 20:47
 */
public interface DictMapper {

    @Select("select * from ${tableName} order by id")
    public List<DictVo> getDictList(@Param("tableName") String tableName);
}
