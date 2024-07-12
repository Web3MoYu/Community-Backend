package com.shixi3.communitybackend.examine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mysql.cj.log.Log;
import com.shixi3.communitybackend.examine.entity.CarVet;
import com.shixi3.communitybackend.examine.vo.CarVetVo;

import java.util.List;

public interface CarVetService extends IService<CarVet> {
    List<CarVetVo> getCarVetByStatus(Integer status);

    List<CarVetVo> getCarVetByUser(Long userId);
}
