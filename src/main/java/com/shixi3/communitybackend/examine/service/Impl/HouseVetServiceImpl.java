package com.shixi3.communitybackend.examine.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.examine.mapper.HouseVetMapper;
import com.shixi3.communitybackend.examine.service.HouseVetService;
import com.shixi3.communitybackend.examine.vo.HouseVetVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class HouseVetServiceImpl extends ServiceImpl<HouseVetMapper, HouseVetVo> implements HouseVetService {
    @Resource
    private HouseVetMapper houseVetMapper;
    @Override
    public Page<HouseVetVo> page(Integer page, Integer pageSize, Integer status) {


        Page<HouseVetVo> result = new Page<>(page,pageSize);
        result = houseVetMapper.page(result,status);
        return result;
    }

    @Override
    public HouseVetVo getHouseVetVoById(Long id) {
        return houseVetMapper.getHouseVetVoById(id);
    }
}
