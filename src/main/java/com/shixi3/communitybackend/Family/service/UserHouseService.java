package com.shixi3.communitybackend.Family.service;

import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.common.entity.User;

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
     * 获取房屋户主
     * @param houseId
     * @return
     */
    User getHouseholdByHouseId(Long houseId);

    /**
     * 获取房屋成员
     * @param houseId
     * @return
     */
    List<User> getHouseMembersByHouseId(Long houseId);
    //还需要获取用户的所有的房屋，用户所住的房屋
}
