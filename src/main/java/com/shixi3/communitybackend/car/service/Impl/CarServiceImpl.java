package com.shixi3.communitybackend.car.service.Impl;

import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.mapper.CarMapper;
import com.shixi3.communitybackend.car.service.CarService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper;

    public CarServiceImpl(CarMapper carMapper) {
        this.carMapper = carMapper;
    }

    @Override
    public Car getCarByOwner(Long owner) {
        return carMapper.getCarByOwner(owner);
    }
}