package com.shixi3.communitybackend.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据Userid获取用户名
     *
     * @param userId
     * @return
     */
    List<String> getAuthorities(Long userId);

    @Select("select * from sys_user where username = #{username}")
    User getUserByUsername(String username);
}




