package com.shixi3.communitybackend.examine.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;
import com.shixi3.communitybackend.examine.mapper.HouseVetMapper;
import com.shixi3.communitybackend.examine.service.HouseVetService;
import com.shixi3.communitybackend.examine.vo.HouseVetVo;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.service.HouseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseVetServiceImpl extends ServiceImpl<HouseVetMapper, TenantExamineRecord> implements HouseVetService {
    @Resource
    private HouseVetMapper houseVetMapper;
    @Resource
    private UserHouseService userHouseService;
    @Resource
    private HouseService houseService;
    @Override
    public Page<HouseVetVo> page(Integer page, Integer pageSize, Integer status) {


        Page<HouseVetVo> result = new Page<>(page,pageSize);
        result = houseVetMapper.page(result,status);
        return result;
    }

    @Override
    public HouseVetVo getHouseVetVoById(Long id) {
        return houseVetMapper.getHouseVetVoById(id);
    }
    @Override
    public List<HouseVetVo> getHouseVetVoByUserId(Long wxUserId) {
        return houseVetMapper.getHouseVetVoByUserId(wxUserId);
    }
    @Override
    public void auditHouseWithUser(HouseVetVo houseVetVo) {
        Integer status = houseVetVo.getStatus();
        // 修改状态
        this.updateById(houseVetVo);

        Integer type = houseVetVo.getUserType();
        // 审核通过
        if(status == 2) {
            if(type==0) {
                // 设置户主为当前用户
                LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(House::getHouseId,houseVetVo.getHouseId());
                House house = houseService.getOne(wrapper);
                house.setOwnerId(houseVetVo.getWxUserId());
                houseService.updateById(house);

                Long owner = houseService.getOwner(houseVetVo.getHouseId());
                if(owner != 0) {
                    // 删除原来户主关系表
                    deleteOwnerUserHouse(owner, houseVetVo.getHouseId());
                    // 设置原来户主最高权限
                    houseService.setTopType(owner);
                }
                // 设置当前用户为户主
                houseService.addUserHouse(houseVetVo.getWxUserId(), houseVetVo.getHouseId(), 0);
                // 设置当前用户最高权限
                houseService.setTopType(houseVetVo.getWxUserId());
            }
            else if(type == 1) {
                // 设置当前用户为租户
                houseService.addUserHouse(houseVetVo.getWxUserId(), houseVetVo.getHouseId(), 2);
                // 设置当前用户最高权限
                houseService.setTopType(houseVetVo.getWxUserId());
            }
        }
    }

    public void deleteOwnerUserHouse(Long ownerId,Long houseId) {
        LambdaQueryWrapper<UserHouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserHouse::getWxUserId,ownerId);
        wrapper.eq(UserHouse::getHouseId,houseId);
        wrapper.eq(UserHouse::getBelongFlag,0);
        userHouseService.remove(wrapper);
    }
}
