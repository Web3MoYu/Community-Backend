package com.shixi3.communitybackend.building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.building.entity.Building;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BuildingMapper extends BaseMapper<Building> {
    Page<Building> page(@Param("page") Page<Building> page, @Param("name") String buildingName);
}
