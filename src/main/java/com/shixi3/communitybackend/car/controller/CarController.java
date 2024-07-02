package com.shixi3.communitybackend.car.controller;

import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.service.CarService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarController {
    @Resource
    private CarService carService;

    @GetMapping("/list/{id}")
    public CommonResult<Car> getCarById(@PathVariable("id") Long id) {
        Car car = carService.getCarByOwner(id);
        return CommonResult.success(car);
    }
}
