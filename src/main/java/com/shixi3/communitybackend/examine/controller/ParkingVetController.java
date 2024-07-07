package com.shixi3.communitybackend.examine.controller;

import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.examine.entity.ParkingVet;
import com.shixi3.communitybackend.examine.service.ParkingVetService;
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
    public CommonResult<List<ParkingVet>> getParkingVetByStatus(@PathVariable Integer status){
        List<ParkingVet> parkingVets=parkingVetService.getParkingVetByStatus(status);
        return CommonResult.success(parkingVets);
    }

    /**
     * 根据审核id删除车位
     * @param id 审核车位编号
     * @return 提示信息
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult<String> deleteParking(@PathVariable Long id){
        boolean delete=parkingVetService.removeById(id);
        if(delete){
            return CommonResult.success("删除车位成功!");
        }
        return CommonResult.error(500,"删除车位失败!");
    }

    @PutMapping("/edit")
    public CommonResult<String> updateParking(@RequestBody ParkingVet parkingVet){
        boolean update=parkingVetService.updateById(parkingVet);
        if(update) {
            return CommonResult.success("修改车位信息成功！");
        }
        return CommonResult.error(500,"修改车位信息失败！");
    }
}
