package com.shixi3.communitybackend.Family.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.vo.MyHouseVo;
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
    @Select("select * from wx_user join user_house on wx_user.id=user_house.wx_user_id where user_house.belong_flag=0 and user_house.house_id=#{houseId}")
    WxUser getHouseHoldByHouseId(Long houseId);

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
    @Select("SELECT house.house_id,house.house_number, building.building_number FROM user_house"+
            " JOIN house ON user_house.house_id = house.house_id"+
            " JOIN building ON house.building_id = building.building_id"+
            " WHERE user_house.belong_flag = 0 AND user_house.wx_user_id=#{wxUserId}")
    List<MyHouseVo> getMyHouses(Long wxUserId);

    /**
     * 获取用户所住的房
     * @param wxUserId
     * @return
     */
    @Select("SELECT house.house_id,house.house_number, building.building_number" +
            " FROM user_house" +
            " JOIN house ON user_house.house_id = house.house_id" +
            " JOIN building ON house.building_id = building.building_id" +
            " WHERE user_house.belong_flag != 0 AND user_house.wx_user_id=#{wxUserId}")
    List<MyHouseVo> getLiveHouses(Long wxUserId);

    /**
     * 获取用户类型，0为户主，2为租户，1为成员
     * @param userId
     * @param houseId
     * @return
     */
    @Select("select belong_flag from user_house where wx_user_id=#{userId} and house_id=#{houseId}")
    List<Integer> getUserHouseBelongFlag(Long userId, Long houseId);

}
