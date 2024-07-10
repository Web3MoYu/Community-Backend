package com.shixi3.communitybackend.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.car.Vo.CarVo;
import com.shixi3.communitybackend.car.entity.Car;

import java.util.List;

public interface CarService extends IService<Car> {

    List<CarVo> getAllCar();
    void deleteByOwner(Long owner);

    CarVo getCarById(Long carId);

    List<CarVo> getCarByLicence(String licence);
}