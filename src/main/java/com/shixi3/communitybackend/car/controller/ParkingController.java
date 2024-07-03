package com.shixi3.communitybackend.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.car.entity.Parking;
import com.shixi3.communitybackend.car.service.ParkingService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parking")
public class ParkingController {
    @Resource
    private ParkingService parkingService;

    /**
     * 查询个人车位
     * @param owner 用户id
     * @return
     */
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

    /**
     * 查询所有车位
     * @return
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('car:parking:list')")
    public CommonResult<List<Parking>> getAllParking(){
        List<Parking> parkings=parkingService.list();
        if (!parkings.isEmpty()) {
            return CommonResult.success(parkings);
        } else {
            return CommonResult.error(1,"车位信息获取失败!");
        }
    }

    /**
     * 新增车位信息
     * @param parking 提交车位信息
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('car:parking:add')")
    public CommonResult<String> addParking(@RequestBody Parking parking){
        boolean save=parkingService.save(parking);
        if(save) {
            return CommonResult.success("新增车位成功！");
        }
        return CommonResult.error(500,"新增车位失败！");
    }
}
