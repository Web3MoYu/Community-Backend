package com.shixi3.communitybackend.Family.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.vo.WxUserVo;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.entity.WxUserTree;
import com.shixi3.communitybackend.Family.mapper.UserHouseMapper;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.Family.entity.UserHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * 拿到树形结构
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public List<WxUserTree> selectWxUser(Integer page, Integer pageSize, String name) {

        //拿到分组id（house_id）
        List<Long> groupId = wxUserMapper.getGroupId();
        List<List<WxUserVo>> userVos = new ArrayList<>();
        List<WxUserTree> wxUserTrees = new ArrayList<>();
        //以house_id分组 以及 查询
        for (Long group : groupId){
            List<WxUserVo> groups = wxUserMapper.getGroups(group, name);
            if(groups.size()>0){
               userVos.add(groups);
            }
        }
        //遍历操作使其为树形结构
        for(List<WxUserVo> list : userVos){
            for(WxUserVo wxUserVo : list){
                if(wxUserVo.getBelongFlag() != 1){
                    WxUserTree wxUserTree = new WxUserTree();
                    wxUserTree.setWxUserVo(wxUserVo);
                    list.remove(wxUserVo);
                    //将剩下的改组内的用户添加的他的children中
                    wxUserTree.setChildren(list);
                    //添加到树形结构中
                    wxUserTrees.add(wxUserTree);
                    break;
                }

            }
        }
        return wxUserTrees;
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




}
