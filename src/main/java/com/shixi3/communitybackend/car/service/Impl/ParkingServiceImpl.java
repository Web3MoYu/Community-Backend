package com.shixi3.communitybackend.car.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.entity.Parking;
import com.shixi3.communitybackend.car.mapper.CarMapper;
import com.shixi3.communitybackend.car.mapper.ParkingMapper;
import com.shixi3.communitybackend.car.service.CarService;
import com.shixi3.communitybackend.car.service.ParkingService;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper,Parking> implements ParkingService{
}
