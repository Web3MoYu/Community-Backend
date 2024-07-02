package com.shixi3.communitybackend.car.service.Impl;

import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.mapper.CarMapper;
import com.shixi3.communitybackend.car.service.CarService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    @Resource
    private CarMapper carMapper;


    @Override
    public Car getCarByOwner(Long owner) {
        return carMapper.getCarByOwner(owner);
    }

    @Override
    public Car getAllCar(){return carMapper.getAllCar();}

    @Override
    public Car getVetCar(){return carMapper.getAllCar();}

}