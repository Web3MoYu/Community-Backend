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

    /**
     * 查看个人车辆信息
     * @param owner 个人ID
     * @return
     */
    @GetMapping("/list/{owner}")
    public CommonResult<Car> getCarById(@PathVariable("owner") Long owner) {
        Car car = carService.getCarByOwner(owner);
        if (car!=null) {
            return CommonResult.success(car);
        }else {
            return CommonResult.error(1,"车辆信息获取失败!");
        }
    }

    /**
     * 查看所有车辆信息
     * @return
     */
    @GetMapping("/all")
    public CommonResult<Car> getAllCar(){
        Car car=carService.getAllCar();
        if (car!=null) {
            return CommonResult.success(car);
        }else {
            return CommonResult.error(1,"车辆信息获取失败!");
        }
    }
}
