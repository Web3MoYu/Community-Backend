package com.shixi3.communitybackend.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.service.CarService;
import com.shixi3.communitybackend.common.entity.Menu;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public CommonResult<List<Car>> getCarById(@PathVariable Long owner) {
        LambdaQueryWrapper<Car> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Car::getOwner,owner);
        List<Car> cars = carService.list(wrapper);
        if (!cars.isEmpty()) {
            return CommonResult.success(cars);
        } else {
            return CommonResult.error(1,"车辆信息获取失败!");
        }
    }

    /**
     * 查看所有车辆信息
     * @return
     */
    @GetMapping("/all")
    public CommonResult<List<Car>> getAllCar(){
        List<Car> cars=carService.list();
        if (cars!=null) {
            return CommonResult.success(cars);
        }else {
            return CommonResult.error(1,"车辆信息获取失败!");
        }
    }
}
