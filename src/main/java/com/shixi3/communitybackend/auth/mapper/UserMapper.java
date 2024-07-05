package com.shixi3.communitybackend.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Update("update sys_user set phone = #{phone}, avatar = #{avatar} where user_id = #{userId}")
    void updatePhoneAndAvatar(@Param("phone") String phone, @Param("userId") Long userId, @Param("avatar") String avatar);

    @Update("update sys_user set password = #{password}, salt = #{salt} where user_id = #{userId}")
    int updatePassword(@Param("userId") Long userId, @Param("password") String password, @Param("salt") String salt);
}




