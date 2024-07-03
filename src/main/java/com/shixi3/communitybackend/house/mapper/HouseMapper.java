package com.shixi3.communitybackend.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.house.entity.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface HouseMapper extends BaseMapper<House> {
    Page<House> page(@Param("page") Page<House> page,@Param("number") String houseNumber);
}
