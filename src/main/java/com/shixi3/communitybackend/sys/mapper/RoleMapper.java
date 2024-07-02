package com.shixi3.communitybackend.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.sys.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select sr.role_id, sr.remark, sr.role_name from sys_role sr inner join " +
            "sys_user_role sur on sr.role_id = sur.role_id where sur.user_id = #{userId}")
    Role getRoleByUserID(Long userId);

    @Update("update sys_user_role set role_id = #{roleId} where user_id = #{userId}")
    int updateRoleByUserId(Long userId, Long roleId);
}
