package com.shixi3.communitybackend.house.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.house.entity.House;

public interface HouseService extends IService<House> {
    Page<House> page(Integer page, Integer pageSize, String houseNumber);
}
