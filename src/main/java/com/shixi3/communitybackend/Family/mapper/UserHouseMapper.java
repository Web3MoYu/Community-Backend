package com.shixi3.communitybackend.Family.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserHouseMapper extends BaseMapper<UserHouse> {
    /**
     * 获取房屋的户主
     * @param houseId
     * @return
     */
    @Select("select user_id from user_house where belong_flag=0 and house_id=#{houseId})")
    User getHouseHoldByHouseId(Long houseId);

    /**
     * 获取房屋的所有成员（除开户主）
     * @param houseId
     * @return
     */
    @Select("select user_id from user_house where belong_flag=1 and house_id=#{houseId}")
    List<User> getHouseMembersByHouseId(Long houseId);
}
