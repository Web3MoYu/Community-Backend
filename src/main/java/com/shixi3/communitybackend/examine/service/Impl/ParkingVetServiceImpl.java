package com.shixi3.communitybackend.examine.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.examine.entity.ParkingVet;
import com.shixi3.communitybackend.examine.mapper.ParkingVetMapper;
import com.shixi3.communitybackend.examine.service.ParkingVetService;
import com.shixi3.communitybackend.examine.vo.ParkingVetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingVetServiceImpl extends ServiceImpl<ParkingVetMapper, ParkingVet> implements ParkingVetService {

    @Autowired ParkingVetMapper parkingVetMapper;

    @Override
    public List<ParkingVetVo> getParkingVetByStatus(Integer status){
        return parkingVetMapper.getParkingVetByStatus(status);
    }
    @Override
    public List<ParkingVetVo> getParkingVetByUser(Long userId){
        return  parkingVetMapper.getParkingVetByUser(userId);
    }
}
