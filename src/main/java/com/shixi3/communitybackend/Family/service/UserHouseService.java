package com.shixi3.communitybackend.Family.service;

import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.entity.WxUser;

import java.util.List;

public interface UserHouseService {
    /**
     * 查看所有用户与房屋关系
     * @param userId
     * @param houseId
     * @return
     */
    List<UserHouse> getAllUserHouseRelationships(Long userId, Long houseId);

    /**
     * 获取一个用户与其所有的房屋关系（包括户主关系，成员关系）
     * @param userId
     * @return
     */
    List<UserHouse> getUserHouseRelationshipsByUserId(Long userId);

    /**
     * 获取一个房屋与其所有的用户关系（包括户主关系，成员关系）
     * @param houseId
     * @return
     */
    List<UserHouse> getUserHouseRelationshipsByHouseId(Long houseId);

    /**
     * 判断是否是该房屋户主
     * @param houseId
     * @param userId
     * @return
     */
    boolean isHouseHold(Long houseId, Long userId);

    /**
     * 判断是否为该房屋家庭成员
     * @param houseId
     * @param userId
     * @return
     */
    boolean isHouseMember(Long houseId, Long userId);

    /**
     * 获取房屋户主
     *
     * @param houseId
     * @return
     */
    WxUser getHouseholdByHouseId(Long houseId);

    /**
     * 获取房屋成员
     *
     * @param houseId
     * @return
     */
    List<WxUser> getHouseMembersByHouseId(Long houseId);

    /**
     * 添加户主
     * 一个房屋只有一个户主
     * 一个用户可以是多个房屋户主
     *
     * @param userHouse
     * @return
     */
    Long addHouseHold(UserHouse userHouse);

    /**
     * 添加家庭成员
     * 一个房屋会有多个成员，一个成员可以住多个房屋
     *
     * @param userHouse
     * @return
     */
    Long addHouseMember(UserHouse userHouse);

    /**
     * 删除家庭成员
     *
     * @param id
     * @return
     */
    Integer deleteHouseMember(Long id);



}
