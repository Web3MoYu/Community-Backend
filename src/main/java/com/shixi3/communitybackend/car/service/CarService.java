package com.shixi3.communitybackend.car.service;

import com.shixi3.communitybackend.car.entity.Car;

public interface CarService {

    Car getCarByOwner(Long owner);
    Car getAllCar();
    Car getVetCar();
}