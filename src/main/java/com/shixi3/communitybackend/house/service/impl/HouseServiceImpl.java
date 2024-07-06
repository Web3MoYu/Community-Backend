package com.shixi3.communitybackend.house.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.mapper.HouseMapper;
import com.shixi3.communitybackend.house.service.HouseService;
import com.shixi3.communitybackend.house.vo.HouseVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {
    @Resource
    private HouseMapper houseMapper;
    @Override
    public Page<HouseVo> page(Integer page, Integer pageSize, String houseNumber) {
        Page<HouseVo> result = new Page<>(page,pageSize);
        result = houseMapper.page(result,houseNumber);
        return result;
    }

    @Override
    public HouseVo getHouseVoById(Long houseId) {
        return houseMapper.getHouseVoById(houseId);
    }
}
