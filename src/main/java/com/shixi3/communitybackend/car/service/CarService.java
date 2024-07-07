package com.shixi3.communitybackend.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.car.entity.Car;

import java.util.List;

public interface CarService extends IService<Car> {

    List<Car> getAllCar();
    void deleteByOwner(Long owner);

    Car getCarById(Long carId);

    List<Car> getCarByLicence(String licence);
}