package com.shixi3.communitybackend.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.sys.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Update("update sys_user_role set role_id = #{roleId} where user_id = #{userId}")
    void updateRole(Long userId, Long roleId);

    // 根据用户id删除对应的角色关系表中的数据
    @Update("delete from sys_user_role where user_id = #{userId}")
    void deleteUserRoleById(Long userId);
}
