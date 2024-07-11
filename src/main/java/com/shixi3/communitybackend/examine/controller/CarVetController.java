package com.shixi3.communitybackend.examine.controller;

import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.examine.entity.CarVet;
import com.shixi3.communitybackend.examine.service.CarVetService;
import com.shixi3.communitybackend.examine.vo.CarVetVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/carvet")
public class CarVetController {

    @Resource
    private CarVetService carVetService;

    /**
     * 根据审核状态查询车辆审核表
     * @param status 审核状态
     * @return 车辆信息表
     */
    @GetMapping("/getCar/{status}")
    public CommonResult<List<CarVetVo>> getCarVetByStatus(@PathVariable Integer status){
        List<CarVetVo> carVets=carVetService.getCarVetByStatus(status);
        System.out.println(carVets);
        return CommonResult.success(carVets);
    }

    /**
     * 根据id删除审核车辆信息
     * @param id 审核id
     * @return 提示信息
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult<String> deleteCar(@PathVariable Long id){
        boolean delete=carVetService.removeById(id);
        if(delete){
            return CommonResult.success("删除车辆成功!");
        }
        return CommonResult.error(500,"删除车辆失败!");
    }

    /**
     * 根据id修改车辆信息
     * @param carVet 车辆信息
     * @return 提示信息
     */
    @PutMapping("/edit")
    public CommonResult<String> updateCar(@RequestBody CarVet carVet){
        boolean update=carVetService.updateById(carVet);
        if(update) {
            return CommonResult.success("修改车辆信息成功！");
        }
        return CommonResult.error(500,"修改车辆信息失败！");
    }

    /**
     * 新增审核车辆
     * @param carVet 审核车辆信息
     * @return 提示信息
     */
    @PostMapping("/add")
    public CommonResult<String> addCar(@RequestBody CarVet carVet){
        carVet.setCreateTime(new Date());
        carVet.setUpdateTime(new Date());
        boolean save = carVetService.save(carVet);
        if (save) {
            return CommonResult.success("新增车辆成功！");
        }
        return CommonResult.error(500, "新增车辆失败！");
    }
}
