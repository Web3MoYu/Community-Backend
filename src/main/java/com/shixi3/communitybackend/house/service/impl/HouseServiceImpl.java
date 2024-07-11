package com.shixi3.communitybackend.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.mapper.HouseMapper;
import com.shixi3.communitybackend.house.service.HouseService;
import com.shixi3.communitybackend.house.vo.HouseVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {
    @Resource
    private HouseMapper houseMapper;
    @Resource
    private UserHouseService userHouseService;
    @Resource
    private WxUserService wxUserService;

    @Override
    public Page<HouseVo> page(Integer page, Integer pageSize, Long buildingId) {
        Page<HouseVo> result = new Page<>(page,pageSize);
        result = houseMapper.page(result,buildingId);
        // 获取租户列表
        List<HouseVo> records = result.getRecords();
        for(int i = 0; i<records.size();i++) {
            HouseVo houseVo = records.get(i);
            houseVo.setTenants(getTenants(houseVo.getHouseId()));
        }
        result.setRecords(records);
        return result;
    }

    @Override
    public HouseVo getHouseVoById(Long houseId) {
        HouseVo houseVo = houseMapper.getHouseVoById(houseId);
        houseVo.setTenants(getTenants(houseId));
        return houseVo;
    }

    private List<WxUser> getTenants(Long houseId) {
        LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserHouse::getHouseId,houseId);
        wrapper.eq(UserHouse::getBelongFlag,2);
        List<UserHouse> userHouses = userHouseService.list(wrapper);
        List<WxUser> wxUsers = new ArrayList<>();
        for(int i = 0; i < userHouses.size();i++) {
            Long id = userHouses.get(i).getWxUserId();
            LambdaQueryWrapper<WxUser> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(WxUser::getId,id);
            WxUser wxUser = wxUserService.getOne(wrapper1);
            if(wxUser != null) {
                wxUsers.add(wxUser);
            }
        }
        return  wxUsers;
    }

    // 通过身份证获取租户
    public WxUser getTenantByIdCard(String idCard) {
        LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WxUser::getIdCard,idCard);
        return wxUserService.getOne(wrapper);
    }

    // 获取家人id列表
    public List<Long> getFamilyIds(Long houseId) {
        LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserHouse::getHouseId,houseId);
        wrapper.eq(UserHouse::getBelongFlag,1);
        List<UserHouse> familyHouse = userHouseService.list(wrapper);
        List<Long> family = new ArrayList<>();
        if(familyHouse.size() > 0) {
            for(UserHouse userHouse:familyHouse) {
                family.add(userHouse.getWxUserId());
            }
        }
        return family;
    }

    // 获取户主id
    public Long getOwner(Long houseId) {
        LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserHouse::getHouseId,houseId);
        wrapper.eq(UserHouse::getBelongFlag,0);
        UserHouse owner = userHouseService.getOne(wrapper);
        if(owner == null) {
            return 0L;
        }
        return owner.getWxUserId();
    }

    public void saveWithUserHouseAndTopType(HouseVo house) {
        Long houseId = house.getHouseId();
        Long ownerId = house.getOwnerId();
        List<String> idCards = house.getTenantCards();

        // 重置房屋
        resetWithUserHouseAndTopType(houseId);

        // 增添新的关系
        if(ownerId != 0) {
            // 添加户主关系
            addUserHouse(ownerId,houseId,0);
            // 设置最高权限
            setTopType(ownerId);
        }
        if(idCards.size() > 0) {
            for(String idCard:idCards) {
                WxUser tenant = getTenantByIdCard(idCard);
                // 添加租户关系
                addUserHouse(tenant.getId(), houseId,2);
                // 设置最高权限
                setTopType(tenant.getId());
            }
        }
    }

    public void resetWithUserHouseAndTopType(Long houseId) {
        // 获取以前的户主、租户和家人
        List<Long> originFamily = getFamilyIds(houseId);
        Long originOwner = getOwner(houseId);
        List<WxUser> originTenants = getTenants(houseId);

        // 清空房屋所有关系
        deleteAllWithUserHouse(houseId);

        // 为以前的户主，租户和家人重新设置最高权限
        if(originOwner != 0) {
            setTopType(originOwner);
        }
        for(WxUser tenant:originTenants) {
            setTopType(tenant.getId());
        }
        for(Long member:originFamily) {
            setTopType(member);
        }
    }

    @Override
    public void deleteWithUser(Long houseId) {
        resetWithUserHouseAndTopType(houseId);
        removeById(houseId);
    }

    @Override
    public void delBatchWithUser(List<Long> houseIds) {
        for(Long houseId:houseIds) {
            deleteWithUser(houseId);
        }
    }

    // 设置最高权限
    public void setTopType(Long userId) {
        LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null, UserHouse::getWxUserId, userId);
        List<UserHouse> userHouses = userHouseService.list(wrapper);

        // 获取最高权限
        int topType = 3;
        if (userHouses.size() > 0) {
            for (UserHouse userHouse : userHouses) {
                int flag = userHouse.getBelongFlag();
                // 最高权限为户主
                if (flag == 0) {
                    topType = 0;
                    break;
                }
            }
            // 最高权限为业主
            if (topType != 0) {
                topType = 2;
            }
        }

        // 设置最高权限
        LambdaQueryWrapper<WxUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(WxUser::getId,userId);
        WxUser wxUser = wxUserService.getOne(userWrapper);
        wxUser.setUserType(topType);
        wxUserService.updateById(wxUser);
    }

    // 删除有关该房屋所有用户房屋关系
    public void deleteAllWithUserHouse(Long houseId) {
        LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(houseId != null,UserHouse::getHouseId,houseId);
        userHouseService.remove(wrapper);
    }

    // 添加用户房屋关系
    public void addUserHouse(Long userId,Long houseId,Integer belongFlag) {
        UserHouse userHouse = new UserHouse();
        userHouse.setWxUserId(userId);
        userHouse.setHouseId(houseId);
        userHouse.setBelongFlag(belongFlag);
        userHouseService.save(userHouse);
    }


}
