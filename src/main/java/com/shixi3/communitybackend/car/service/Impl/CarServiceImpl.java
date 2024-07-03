package com.shixi3.communitybackend.car.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.building.entity.Building;
import com.shixi3.communitybackend.building.mapper.BuildingMapper;
import com.shixi3.communitybackend.building.service.BuildingService;
import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.mapper.CarMapper;
import com.shixi3.communitybackend.car.service.CarService;
import com.shixi3.communitybackend.examine.mapper.UserInformationMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper,Car> implements CarService{

}