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
            LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserHouse::getHouseId,houseVo.getHouseId());
            wrapper.eq(UserHouse::getBelongFlag,2);
            List<UserHouse> userHouses = userHouseService.list(wrapper);
            List<WxUser> wxUsers = new ArrayList<>();
            for(int j = 0; j < userHouses.size();j++) {
                Long id = userHouses.get(j).getWxUserId();
                LambdaQueryWrapper<WxUser> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(WxUser::getId,id);
                WxUser wxUser = wxUserService.getOne(wrapper1);
                if(wxUser != null) {
                    wxUsers.add(wxUser);
                }
            }
            houseVo.setTenants(wxUsers);
        }
        result.setRecords(records);
        return result;
    }

    @Override
    public HouseVo getHouseVoById(Long houseId) {
        return houseMapper.getHouseVoById(houseId);
    }

    @Override
    public void saveHouseWithUser(List<String> tenantCards,House house) {
        long ownerId = 0;
        Integer state = house.getState();

        if(state == 1 || state == 2) {
            UserHouse userHouse = new UserHouse();
            userHouse.setHouseId(house.getHouseId());
            // 户主
            ownerId = house.getOwnerId();
            userHouse.setWxUserId(ownerId);
            userHouse.setBelongFlag(0);
            userHouseService.save(userHouse);
        }
        if(state == 2) {
            // 租户
            for(String idCard:tenantCards) {
                UserHouse userHouse = new UserHouse();
                userHouse.setHouseId(house.getHouseId());
                LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(idCard != null,WxUser::getIdCard,idCard);
                WxUser wxUser = wxUserService.getOne(wrapper);
                if(wxUser != null) {
                    userHouse.setWxUserId(wxUser.getId());
                    userHouse.setBelongFlag(2);
                    userHouseService.save(userHouse);
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
}
