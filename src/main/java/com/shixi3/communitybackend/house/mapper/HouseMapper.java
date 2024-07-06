package com.shixi3.communitybackend.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.vo.HouseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseMapper extends BaseMapper<House> {
    Page<HouseVo> page(@Param("page") Page<HouseVo> page, @Param("number") String houseNumber);

    HouseVo getHouseVoById(Long houseId);
}
