package com.shixi3.communitybackend.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.car.Vo.ParkingVo;
import com.shixi3.communitybackend.car.entity.Parking;

import java.util.List;

public interface ParkingService extends IService<Parking> {
    List<ParkingVo> getAllParking();

    ParkingVo getParkingById(Long parkingId);

    List<ParkingVo> getParkingByNumber(String number);

    List<Parking> getParkingNoOwner();
}
