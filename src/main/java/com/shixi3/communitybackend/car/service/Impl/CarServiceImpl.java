package com.shixi3.communitybackend.car.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.mapper.CarMapper;
import com.shixi3.communitybackend.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper,Car> implements CarService{

    @Autowired
    private CarMapper carMapper;

    @Override
    public List<Car> getAllCar() {
        return carMapper.getAllCar();
    }

    @Override
    public void deleteByOwner(Long owner) {
        carMapper.deleteByOwner(owner);
    }
}