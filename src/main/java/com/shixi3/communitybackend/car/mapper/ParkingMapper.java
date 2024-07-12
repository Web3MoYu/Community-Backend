package com.shixi3.communitybackend.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.car.Vo.ParkingVo;
import com.shixi3.communitybackend.car.entity.Parking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParkingMapper extends BaseMapper<Parking> {
    List<ParkingVo> getAllParking();

    ParkingVo getParkingById(Long parkingId);

    List<ParkingVo> getParkingByNumber(String number);

    List<Parking> getParkingNoOwner();
}
