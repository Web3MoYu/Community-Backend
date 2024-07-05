package com.shixi3.communitybackend.Family.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.mapper.UserHouseMapper;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.Family.entity.UserHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UserHouseServiceImpl implements UserHouseService {
    @Autowired
    private UserHouseMapper userHouseMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    /**
     * 获取全部用户房屋关系
     * @param userId
     * @param houseId
     * @return
     */
    @Override
    public List<UserHouse> getAllUserHouseRelationships(Long userId, Long houseId) {
        return userHouseMapper.selectList(null);
    }

    /**
     * 获取一个用户与其所有的房屋关系（包括户主关系，成员关系）
     * @param userId
     * @return
     */
    @Override
    public List<UserHouse> getUserHouseRelationshipsByUserId(Long userId) {
        LambdaQueryWrapper<UserHouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHouse::getWxUserId, userId);
        return userHouseMapper.selectList(queryWrapper);
    }

    /**
     * 获取一个房屋与其所有的用户关系（包括户主关系，成员关系）
     * @param houseId
     * @return
     */
    @Override
    public List<UserHouse> getUserHouseRelationshipsByHouseId(Long houseId) {
        LambdaQueryWrapper<UserHouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHouse::getHouseId, houseId);
        return userHouseMapper.selectList(queryWrapper);
    }

    /**
     * 判断用户是否为该房屋户主
     * @param houseId
     * @param userId
     * @return
     */
    public boolean isHouseHold(Long houseId, Long userId){
        LambdaQueryWrapper<UserHouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHouse::getHouseId, houseId);
        queryWrapper.eq(UserHouse::getWxUserId, userId);
        UserHouse userHouse = userHouseMapper.selectOne(queryWrapper);
        if(userHouse.getBelongFlag()==0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断是否为家庭成员
     * @param houseId
     * @param userId
     * @return
     */
    public boolean isHouseMember(Long houseId, Long userId){
        LambdaQueryWrapper<UserHouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHouse::getHouseId, houseId);
        queryWrapper.eq(UserHouse::getWxUserId, userId);
        UserHouse userHouse = userHouseMapper.selectOne(queryWrapper);
        if(userHouse.getBelongFlag()==1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取房屋户主
     *
     * @param houseId
     * @return
     */
    @Override
    public WxUser getHouseholdByHouseId(Long houseId) {
        return wxUserMapper.selectById(userHouseMapper.getHouseHoldIdByHouseId(houseId));
    }

    /**
     * 获取房屋成员（除开户主）
     *
     * @param houseId
     * @return
     */
    @Override
    public List<WxUser> getHouseMembersByHouseId(Long houseId) {
        return userHouseMapper.getHouseMembersByHouseId(houseId);
    }

    /**
     * 添加户主
     * 一个房屋只有一个户主
     * 一个用户可以是多个房屋户主
     *
     * @param userHouse
     * @return
     */
    @Override
    public Long addHouseHold(UserHouse userHouse) {
        userHouse.setBelongFlag(0);
        userHouseMapper.insert(userHouse);
        return userHouse.getId();
    }

    /**
     * 添加家庭成员
     * 一个房屋会有多个成员，一个成员可以住多个房屋
     *
     * @param userHouse
     * @return
     */
    @Override
    public Long addHouseMember(UserHouse userHouse) {
        userHouse.setBelongFlag(1);
        userHouseMapper.insert(userHouse);
        return userHouse.getId();
    }

    /**
     * 删除用户房屋关系
     * 后台管理员可删除户主，成员，租户
     * 前台户主可删除家庭成员
     * @param id
     * @return
     */
    @Override
    public Integer deleteHouseMember(Long id) {
        return  userHouseMapper.deleteById(id);
    }

}
