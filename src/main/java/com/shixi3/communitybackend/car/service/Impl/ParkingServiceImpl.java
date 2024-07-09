package com.shixi3.communitybackend.car.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.car.entity.Parking;
import com.shixi3.communitybackend.car.mapper.ParkingMapper;
import com.shixi3.communitybackend.car.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper,Parking> implements ParkingService{
    @Autowired
    private ParkingMapper parkingMapper;

    @Override
    public List<Parking> getAllParking() {
        return parkingMapper.getAllParking();
    }

    @Override
    public Parking getParkingById(Long parkingId){ return parkingMapper.getParkingById(parkingId); }

    @Override
    public List<Parking> getParkingByNumber(String number){
        return parkingMapper.getParkingByNumber(number);
    }
}
