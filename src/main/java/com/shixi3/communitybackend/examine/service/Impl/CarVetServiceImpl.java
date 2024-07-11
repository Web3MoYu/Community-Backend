package com.shixi3.communitybackend.examine.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.examine.entity.CarVet;
import com.shixi3.communitybackend.examine.mapper.CarVetMapper;
import com.shixi3.communitybackend.examine.service.CarVetService;
import com.shixi3.communitybackend.examine.vo.CarVetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarVetServiceImpl extends ServiceImpl<CarVetMapper, CarVet> implements CarVetService {

    @Autowired CarVetMapper carVetMapper;

    @Override
    public List<CarVetVo> getCarVetByStatus(Integer status){
        return carVetMapper.getCarVetByStatus(status);
    }
}
