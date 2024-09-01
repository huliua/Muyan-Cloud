package com.muyan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muyan.domain.entity.UserRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-04-28 22:15
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("select dm from t_role left join t_user_role on t_role.id = t_user_role.role_id where t_user_role.user_id = #{userId}")
    List<String> selectByUserId(Long userId);
}
