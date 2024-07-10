package com.shixi3.communitybackend.car.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.car.Vo.CarVo;
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
    public List<CarVo> getAllCar() {
        return carMapper.getAllCar();
    }

    @Override
    public void deleteByOwner(Long owner) {
        carMapper.deleteByOwner(owner);
    }

    @Override
    public CarVo getCarById(Long carId) {
        return carMapper.getCarById(carId);
    }

    @Override
    public List<CarVo> getCarByLicence(String licence){
        return carMapper.getCarByLicence(licence);
    }
}