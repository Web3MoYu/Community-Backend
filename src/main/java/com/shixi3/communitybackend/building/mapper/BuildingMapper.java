package com.shixi3.communitybackend.building.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.building.entity.Building;
import com.shixi3.communitybackend.building.vo.BuildingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BuildingMapper extends BaseMapper<Building> {
    Page<BuildingVo> page(@Param("page") Page<BuildingVo> page, @Param("number") Integer buildingNumber);
}
