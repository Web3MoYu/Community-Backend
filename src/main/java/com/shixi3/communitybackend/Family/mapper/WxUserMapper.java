package com.shixi3.communitybackend.Family.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.Family.vo.WxUserVo;
import com.shixi3.communitybackend.Family.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

    @Select("select distinct * from wx_user where user_type = 0;")
     List<WxUserVo> getParentId();

    @Select("select * from wx_user where (user_type = 1 or user_type = 2) and parent_id = #{parentId} and name like concat('%',#{name}, '%');")
    List<WxUserVo> getGroups(Long parentId, String name);
}
