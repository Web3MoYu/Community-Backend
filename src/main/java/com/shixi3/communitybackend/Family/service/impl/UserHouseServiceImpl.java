package com.shixi3.communitybackend.Family.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserHouseServiceImpl extends ServiceImpl<UserHouseMapper,UserHouse> implements UserHouseService {
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

        //拿到所有户主id
//        List<WxUserVo> parentIds = wxUserMapper.getParentId();

        //最后所需的树形数据存储
        List<WxUserTree> wxUserTrees = new ArrayList<>();
        //以parent_id分组 以及 查询
//        for (WxUserVo parent : parentIds){
        List<WxUserVo> groups = wxUserMapper.getGroups(name);
//            if(!name.isEmpty()){
        //看户主是否满足查询要求
//                if(parent.getName().contains(name)) {
//                    WxUserTree wxUserTree = new WxUserTree();
//                    wxUserTree.setWxUserVo(parent);
//                    wxUserTree.setChildren(null);
//                    wxUserTrees.add(wxUserTree);
//                }
        if (!groups.isEmpty()) {
            for (WxUserVo group : groups) {
                WxUserTree wxUserTree = new WxUserTree();
                wxUserTree.setWxUserVo(group);
                wxUserTree.setChildren(null);
                wxUserTrees.add(wxUserTree);
            }
        }
//                }
//            }else{
//                WxUserTree wxUserTree = new WxUserTree();
//                wxUserTree.setWxUserVo(parent);
//                //将剩下的改组内的用户添加的他的children中
//                wxUserTree.setChildren(groups);
//                //添加到树形结构中
//                wxUserTrees.add(wxUserTree);
//            }
//
//        }
            //标识是否已经存在
            //遍历操作使其为树形结构
//        for(List<WxUserVo> list : userVos){
//            for(WxUserVo wxUserVo : list){
//                if(wxUserVo.getBelongFlag() != 1){
//                    WxUserTree wxUserTree = new WxUserTree();
//                    wxUserTree.setWxUserVo(wxUserVo);
//                    list.remove(wxUserVo);
//                    //将剩下的改组内的用户添加的他的children中
//                    wxUserTree.setChildren(list);
//
//                    break;
//                }
//            }
//        }
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
     * 获取一个房屋与其所有的用户（包括户主，成员，租户）
     * @param houseId
     * @return
     */
    @Override
    public List<WxUser> getUsersByHouseId(Long houseId) {
        return userHouseMapper.getUsersByHouseId(houseId);
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
        if(userHouse==null){
            return false;
        }else {
            if(userHouse.getBelongFlag()==1){
                return true;
            }else {
                return false;
            }
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
        return userHouseMapper.getHouseHoldByHouseId(houseId);
    }

    /**
     * 获取房屋户主关系
     * @param houseId
     * @return
     */
    @Override
    public UserHouse getHouseholdRelationship(Long houseId) {
        LambdaQueryWrapper<UserHouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHouse::getHouseId, houseId);
        queryWrapper.eq(UserHouse::getBelongFlag,0);
        return userHouseMapper.selectOne(queryWrapper);
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
     * 获取房屋所有租户
     * @param houseId
     * @return
     */
    @Override
    public List<WxUser> getTenantsByHouseId(Long houseId) {
        return userHouseMapper.getTenantsByHouseId(houseId);
    }

    /**
     *
     * 根据房号和微信用户号（微信用户表主键）获取成员
     * @param houseId
     * @param wxUserId
     * @return
     */
    @Override
    public UserHouse getOneHouseMember(Long houseId, Long wxUserId) {
        LambdaQueryWrapper<UserHouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHouse::getHouseId, houseId);
        queryWrapper.eq(UserHouse::getWxUserId, wxUserId);
        return userHouseMapper.selectOne(queryWrapper);
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
     * 过户
     * 户主角色操作
     * @param houseId
     * @param wxUserId
     * @return
     */
    @Override
    public boolean updateHouseHold(Long houseId, Long wxUserId) {
        UserHouse originalHouseHold = getHouseholdRelationship(houseId);
        originalHouseHold.setBelongFlag(1);
        Long i,j;
        if(isHouseMember(originalHouseHold.getHouseId(),wxUserId)){
            UserHouse userHouse=getOneHouseMember(houseId,wxUserId);
            userHouse.setBelongFlag(0);
            i= (long) userHouseMapper.updateById(userHouse);
        }else {
            UserHouse userHouse=new UserHouse();
            userHouse.setHouseId(houseId);
            userHouse.setWxUserId(wxUserId);
            userHouse.setBelongFlag(0);
            i=addHouseHold(userHouse);
        }
        j= (long) userHouseMapper.updateById(originalHouseHold);
        if(i!=0&&j!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 添加家庭成员,租客
     *
     *
     * @param userHouse
     * @return
     */
    @Override
    public Long addHouseMemberTenant(UserHouse userHouse) {
        userHouseMapper.insert(userHouse);
        return userHouse.getId();
    }

    /**
     * 删除用户房屋关系
     * 后台管理员可删除户主，成员，租户
     * 前台户主可删除家庭成员
     * @param userId
     * @param houseId
     * @param belongFlag
     * @return
     */
    @Override
    public Integer deleteHouseMemberTenant(Long userId,Long houseId,Integer belongFlag) {
        LambdaQueryWrapper<UserHouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHouse::getHouseId, houseId);
        queryWrapper.eq(UserHouse::getBelongFlag, belongFlag);
        queryWrapper.eq(UserHouse::getWxUserId, userId);
        return userHouseMapper.delete(queryWrapper);
    }

}
