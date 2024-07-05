package com.shixi3.communitybackend.Family.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.Family.vo.WxUserVo;
import com.shixi3.communitybackend.Family.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

    @Select("select distinct house_id from user_house;")
     List<Long> getGroupId();

    @Select("select  house_id, belong_flag, wx_user.* from user_house, " +
            "wx_user where wx_user_id = wx_user.id and user_house.house_id = #{groupId} and name like concat('%',#{name}, '%');")
    List<WxUserVo> getGroups(Long groupId, String name);
}
