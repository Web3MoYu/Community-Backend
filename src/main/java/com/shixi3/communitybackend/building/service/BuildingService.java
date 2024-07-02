package com.shixi3.communitybackend.building.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.building.entity.Building;

public interface BuildingService extends IService<Building> {
    Page<Building> page(Integer page, Integer pageSize, String buildingName);
}
