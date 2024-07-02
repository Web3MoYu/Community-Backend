package com.shixi3.communitybackend.UserHouseRelation.service;

import com.shixi3.communitybackend.UserHouseRelation.entity.UserHouseRelationship;

import java.util.List;

public interface UserHouseRelationshipService {
    /**
     * 查看所有用户与房屋关系
     * @param userId
     * @param houseId
     * @return
     */
    List<UserHouseRelationship> getAllUserHouseRelationships(Long userId, Long houseId);

    /**
     * 获取一个用户与其所有的房屋关系（包括户主关系，成员关系）
     * @param userId
     * @return
     */
    List<UserHouseRelationship> getUserHouseRelationshipsByUserId(Long userId);

    /**
     * 获取一个房屋与其所有的用户关系（包括户主关系，成员关系）
     * @param houseId
     * @return
     */
    List<UserHouseRelationship> getUserHouseRelationshipsByHouseId(Long houseId);
}
