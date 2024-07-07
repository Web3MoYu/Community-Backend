package com.shixi3.communitybackend.examine.controller;

import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.examine.entity.CarVet;
import com.shixi3.communitybackend.examine.service.CarVetService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/carvet")
public class CarVetController {

    @Resource
    private CarVetService carVetService;

    @GetMapping("/getCar/{status}")
    public CommonResult<List<CarVet>> getCarVetByStatus(@PathVariable Integer status){
        List<CarVet> carVets=carVetService.getCarVetByStatus(status);
        System.out.println(carVets);
        return CommonResult.success(carVets);
    }
}
