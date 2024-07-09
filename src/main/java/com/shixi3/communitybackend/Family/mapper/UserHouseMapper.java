package com.shixi3.communitybackend.Family.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.house.entity.House;
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
    @Select("select wx_user_id from user_house where belong_flag=0 and house_id=#{houseId}")
    Long getHouseHoldIdByHouseId(Long houseId);

    /**
     * 获取房屋的所有住户
     * @param houseId
     * @return
     */
    @Select("select * from wx_user join user_house on wx_user.id=user_house.wx_user_id where user_house.house_id=#{houseId}")
    List<WxUser> getUsersByHouseId(Long houseId);
    /**
     * 获取房屋的所有成员（除开户主）
     * @param houseId
     * @return
     */
    @Select("select * from wx_user join user_house on wx_user.id=user_house.wx_user_id where user_house.belong_flag=1 and user_house.house_id=#{houseId}")
    List<WxUser> getHouseMembersByHouseId(Long houseId);

    /**
     * 获取房屋所有租户
     * @param houseId
     * @return
     */
    @Select("select * from wx_user join user_house on wx_user.id=user_house.wx_user_id where user_house.belong_flag=2 and user_house.house_id=#{houseId}")
    List<WxUser> getTenantsByHouseId(Long houseId);

    /**
     * 获取用户作为户主的房
     * @param wxUserId
     * @return
     */
    @Select("select * from house join user_house on house.house_id=user_house.house_id where user_house.belong_flag=0 and user_house.wx_user_id=#{wxUserId}")
    List<House> getMyHouses(Long wxUserId);

    /**
     * 获取用户所住的房
     * @param wxUserId
     * @return
     */
    @Select("select * from house join user_house on house.house_id=user_house.house_id where user_house.belong_flag!=0 and user_house.wx_user_id=#{wxUserId}")
    List<House> getLiveHouses(Long wxUserId);

}
