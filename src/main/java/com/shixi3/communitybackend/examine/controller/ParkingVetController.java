package com.shixi3.communitybackend.examine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.car.entity.Parking;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.examine.entity.ParkingVet;
import com.shixi3.communitybackend.examine.service.ParkingVetService;
import com.shixi3.communitybackend.examine.vo.ParkingVetVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parkingvet")
public class ParkingVetController {
    @Resource
    private ParkingVetService parkingVetService;

    /**
     * 根据审核状态查询车位审核表
     * @param status 审核状态
     * @return 车位信息表
     */
    @GetMapping("/getParking/{status}")
//    @PreAuthorize("hasAuthority('')")
    public CommonResult<List<ParkingVetVo>> getParkingVetByStatus(@PathVariable Integer status){
        List<ParkingVetVo> parkingVets=parkingVetService.getParkingVetByStatus(status);
        return CommonResult.success(parkingVets);
    }

    /**
     * 根据审核id删除车位
     * @param vetId 审核车位编号
     * @return 提示信息
     */
    @DeleteMapping("/delete/{vetId}")
    public CommonResult<String> deleteParking(@PathVariable Long vetId){
        LambdaQueryWrapper<ParkingVet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ParkingVet::getVetId, vetId);
        boolean delete=parkingVetService.remove(wrapper);
        if(delete){
            return CommonResult.success("删除车位成功!");
        }
        return CommonResult.error(500,"删除车位失败!");
    }

    /**
     * 根据id修改车位信息
     * @param parkingVet 修改的车位信息
     * @return 提示信息
     */
    @PutMapping("/edit")
    public CommonResult<String> updateParking(@RequestBody ParkingVet parkingVet){
        boolean update=parkingVetService.updateById(parkingVet);
        if(update) {
            return CommonResult.success("修改车位信息成功！");
        }
        return CommonResult.error(500,"修改车位信息失败！");
    }

    @PostMapping("/add")
    public CommonResult<String> addParking(@RequestBody ParkingVet parkingVet){
        boolean save=parkingVetService.save(parkingVet);
        if (save) {
            return CommonResult.success("新增车位成功！");
        }
        return CommonResult.error(500, "新增车位失败！");
    }

    @GetMapping("/list/{userId}")
    public CommonResult<List<ParkingVetVo>> getParkingVetByUser(@PathVariable Long userId){
        List<ParkingVetVo> parkingVetVos=parkingVetService.getParkingVetByUser(userId);
        return CommonResult.success(parkingVetVos);
    }
}
