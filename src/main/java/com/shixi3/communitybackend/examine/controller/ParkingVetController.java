package com.shixi3.communitybackend.examine.controller;

import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.examine.entity.ParkingVet;
import com.shixi3.communitybackend.examine.service.ParkingVetService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parkingvet")
public class ParkingVetController {
    @Resource
    private ParkingVetService parkingVetService;

    /**
     * 根据审核状态查询车辆审核表
     * @param status 审核状态
     * @return 车辆信息表
     */
    @GetMapping("/getParking/{status}")
//    @PreAuthorize("hasAuthority('')")
    public CommonResult<List<ParkingVet>> getParkingVetByStatus(@PathVariable Integer status){
        List<ParkingVet> parkingVets=parkingVetService.getParkingVetByStatus(status);
        return CommonResult.success(parkingVets);
    }
}
