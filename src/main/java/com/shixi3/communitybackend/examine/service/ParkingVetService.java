package com.shixi3.communitybackend.examine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.examine.entity.ParkingVet;

import java.util.List;

public interface ParkingVetService extends IService<ParkingVet> {
    List<ParkingVet> getParkingVetByStatus(Integer status);
}
