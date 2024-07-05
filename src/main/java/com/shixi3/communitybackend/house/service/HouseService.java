package com.shixi3.communitybackend.house.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.vo.HouseVo;

public interface HouseService extends IService<House> {
    Page<HouseVo> page(Integer page, Integer pageSize, String houseNumber);
    HouseVo getHouseVoById(Long houseId);
}
