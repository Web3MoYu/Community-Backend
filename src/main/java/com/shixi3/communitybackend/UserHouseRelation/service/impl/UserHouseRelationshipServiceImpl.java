package com.shixi3.communitybackend.UserHouseRelation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.UserHouseRelation.mapper.UserHouseRelationshipMapper;
import com.shixi3.communitybackend.UserHouseRelation.service.UserHouseRelationshipService;
import com.shixi3.communitybackend.UserHouseRelation.entity.UserHouseRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserHouseRelationshipServiceImpl implements UserHouseRelationshipService {
    @Autowired
    private UserHouseRelationshipMapper userHouseRelationshipMapper;

    /**
     * 获取全部用户房屋关系
     * @param userId
     * @param houseId
     * @return
     */
    @Override
    public List<UserHouseRelationship> getAllUserHouseRelationships(Long userId, Long houseId) {
        return userHouseRelationshipMapper.selectList(null);
    }

    /**
     * 获取一个用户与其所有的房屋关系（包括户主关系，成员关系）
     * @param userId
     * @return
     */
    @Override
    public List<UserHouseRelationship> getUserHouseRelationshipsByUserId(Long userId) {
        LambdaQueryWrapper<UserHouseRelationship> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHouseRelationship::getUserId, userId);
        return List.of();
    }

    /**
     * 获取一个房屋与其所有的用户关系（包括户主关系，成员关系）
     * @param houseId
     * @return
     */
    @Override
    public List<UserHouseRelationship> getUserHouseRelationshipsByHouseId(Long houseId) {
        return List.of();
    }
}
