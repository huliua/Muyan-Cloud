package com.muyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-28 22:15
 */
public interface RolePermissionMapper extends BaseMapper<RolePermissionMapper> {

    @Select("select p.dm\n" +
            "from t_permission p\n" +
            "left join `t_role_permission` rp on rp.permission_id=p.id\n" +
            "left join `t_user_role` ur on ur.role_id=rp.role_id\n" +
            "where ur.user_id=#{userId}")
    List<String> selectByUserId(Long userId);
}
