package com.shixi3.communitybackend.examine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.examine.entity.ParkingVet;
import com.shixi3.communitybackend.examine.vo.ParkingVetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParkingVetMapper extends BaseMapper<ParkingVet> {
    List<ParkingVetVo> getParkingVetByStatus(Integer status);

    List<ParkingVetVo> getParkingVetByUser(Long userId);
}
