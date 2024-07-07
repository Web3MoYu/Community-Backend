package com.shixi3.communitybackend.examine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.examine.entity.CarVet;

import java.util.List;

public interface CarVetService extends IService<CarVet> {
    List<CarVet> getCarVetByStatus(Integer status);
}
