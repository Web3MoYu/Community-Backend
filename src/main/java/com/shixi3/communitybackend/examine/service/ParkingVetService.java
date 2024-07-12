package com.shixi3.communitybackend.examine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.examine.entity.ParkingVet;
import com.shixi3.communitybackend.examine.vo.ParkingVetVo;

import java.util.List;

public interface ParkingVetService extends IService<ParkingVet> {
    List<ParkingVetVo> getParkingVetByStatus(Integer status);

    List<ParkingVetVo> getParkingVetByUser(Long userId);
}
