package com.shixi3.communitybackend.house.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.vo.HouseVo;

import java.util.List;

public interface HouseService extends IService<House> {
    Page<HouseVo> page(Integer page, Integer pageSize, Long buildingId);
    HouseVo getHouseVoById(Long houseId);
    void saveWithUserHouseAndTopType(HouseVo house);

    void deleteWithUser(Long houseId);

    void delBatchWithUser(List<Long> houseIds);

}
