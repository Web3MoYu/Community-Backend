package com.shixi3.communitybackend.building.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.building.entity.Building;
import com.shixi3.communitybackend.building.vo.BuildingVo;

public interface BuildingService extends IService<Building> {
    Page<BuildingVo> page(Integer page, Integer pageSize, Integer buildingNumber);
}
