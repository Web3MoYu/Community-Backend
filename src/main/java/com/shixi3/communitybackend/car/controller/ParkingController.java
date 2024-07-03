package com.shixi3.communitybackend.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.car.entity.Parking;
import com.shixi3.communitybackend.car.service.ParkingService;
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
@RequestMapping("/parking")
public class ParkingController {
    @Resource
    private ParkingService parkingService;

    @GetMapping("/list/{owner}")
    public CommonResult<List<Parking>> getParkingById(@PathVariable Long owner) {
        LambdaQueryWrapper<Parking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Parking::getOwner,owner);
        List<Parking> parkings = parkingService.list(wrapper);
        if (!parkings.isEmpty()) {
            return CommonResult.success(parkings);
        } else {
            return CommonResult.error(1,"车位信息获取失败!");
        }
    }

    @GetMapping("/all")
    public CommonResult<List<Parking>> getAllParking(){
        System.out.println("**************tset1**************");
        List<Parking> parkings=parkingService.list();
        if (!parkings.isEmpty()) {
            return CommonResult.success(parkings);
        } else {
            return CommonResult.error(1,"车位信息获取失败!");
        }
    }
}
