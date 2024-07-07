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
    public Page<HouseVo> page(Integer page, Integer pageSize, String houseNumber) {
        Page<HouseVo> result = new Page<>(page,pageSize);
        result = houseMapper.page(result,houseNumber);
        List<HouseVo> records = result.getRecords();
        for(int i = 0; i<records.size();i++) {
            HouseVo houseVo = records.get(i);
            getTenants(houseVo);
        }
        result.setRecords(records);
        return result;
    }

    @Override
    public HouseVo getHouseVoById(Long houseId) {
        HouseVo houseVo = houseMapper.getHouseVoById(houseId);
        getTenants(houseVo);
        return houseVo;
    }

    private void getTenants(HouseVo houseVo) {
        LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserHouse::getHouseId,houseVo.getHouseId());
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
        houseVo.setTenants(wxUsers);
    }

    @Override
    public void saveHouseWithUser(List<String> tenantCards,HouseVo house) {
        long ownerId = 0;
        Integer state = house.getState();

        if(state == 1 || state == 2) {
            LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserHouse::getHouseId,house.getHouseId());
            wrapper.eq(UserHouse::getHouseId,house.getOwnerId());
            wrapper.eq(UserHouse::getBelongFlag,0);
            UserHouse userHouse = userHouseService.getOne(wrapper);
            // 如果没有户主关系
            if(userHouse == null) {
                //删除原有户主关系
                LambdaQueryWrapper<UserHouse> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(UserHouse::getHouseId,house.getHouseId());
                wrapper1.eq(UserHouse::getBelongFlag,0);
                userHouseService.remove(wrapper1);

                UserHouse userHouseAdd = new UserHouse();
                userHouseAdd.setHouseId(house.getHouseId());

                // 户主
                ownerId = house.getOwnerId();
                userHouseAdd.setWxUserId(ownerId);
                userHouseAdd.setBelongFlag(0);
                userHouseService.save(userHouseAdd);
            }
        }
        if(state == 2) {
            LambdaQueryWrapper<UserHouse> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(UserHouse::getHouseId,house.getHouseId());
            wrapper2.eq(UserHouse::getBelongFlag,2);
            userHouseService.remove(wrapper2);
            // 租户
            for(String idCard:tenantCards) {
                LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(UserHouse::getHouseId,house.getHouseId());
                wrapper.eq(UserHouse::getBelongFlag,2);

                // 租户信息
                LambdaQueryWrapper<WxUser> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(idCard != null,WxUser::getIdCard,idCard);
                WxUser wxUser = wxUserService.getOne(wrapper1);
                wrapper.eq(UserHouse::getWxUserId,wxUser.getId());
                UserHouse userHouse = userHouseService.getOne(wrapper);

                // 如果不存在租户关系
                if(userHouse == null) {
                    UserHouse userHouseAdd = new UserHouse();
                    userHouseAdd.setHouseId(house.getHouseId());
                    userHouseAdd.setWxUserId(wxUser.getId());
                    userHouseAdd.setBelongFlag(2);
                    userHouseService.save(userHouseAdd);

                }

            }
        }
        if(state == 0) {
            // 未出售 删除所有与该房屋有关系的记录
            LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(house.getHouseId() != null,UserHouse::getHouseId,house.getHouseId());
            userHouseService.remove(wrapper);
        }
    }

    @Override
    public void deleteWithUser(Long houseId) {
        //删除用户房屋关系
        LambdaQueryWrapper<UserHouse> userHouseWrapper = new LambdaQueryWrapper<>();
        userHouseWrapper.eq(houseId != null,UserHouse::getHouseId,houseId);
        userHouseService.remove(userHouseWrapper);

        // 删除房屋信息
        LambdaQueryWrapper<House> houseWrapper = new LambdaQueryWrapper<>();
        houseWrapper.eq(houseId!=null,House::getHouseId,houseId);
        this.remove(houseWrapper);
    }

    @Override
    public void delBatchWithUser(List<Long> houseIds) {
        for(Long houseId:houseIds) {
            deleteWithUser(houseId);
        }
    }
}
