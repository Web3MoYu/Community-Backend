package com.shixi3.communitybackend.examine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.examine.entity.CarVet;
import com.shixi3.communitybackend.examine.entity.ParkingVet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarVetMapper extends BaseMapper<CarVet> {
    List<CarVet> getCarVetByStatus(Integer status);
}
