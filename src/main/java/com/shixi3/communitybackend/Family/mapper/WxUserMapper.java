package com.shixi3.communitybackend.Family.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.Family.vo.CountUserInBuilding;
import com.shixi3.communitybackend.Family.vo.WxUserVo;
import com.shixi3.communitybackend.Family.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

    @Select("select distinct * from wx_user where user_type = 0;")
    List<WxUserVo> getParentId();

    //    @Select("select * from wx_user where (user_type = 1 or user_type = 2) and parent_id = #{parentId} and name like concat('%',#{name}, '%');")
    @Select("select * from wx_user where name like concat('%',#{name}, '%');")
    List<WxUserVo> getGroups(String name);

    List<WxUserVo> getGroupsByParent(Long id, String name);

    @Select("select count(*) number, building_number\n" +
            "from user_house uh\n" +
            "         left join house h on uh.house_id = h.house_id\n" +
            "         left join building b on h.building_id = b.building_id\n" +
            "group by building_number\n")
    List<CountUserInBuilding> countInBuilding();

    @Update("update wx_user set face_image = #{name} where wx_id = #{id}")
    void updateFaceImag(@Param("name") String imageName, @Param("id") String userID);
}
