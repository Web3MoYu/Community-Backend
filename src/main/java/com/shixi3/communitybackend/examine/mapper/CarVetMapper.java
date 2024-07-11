package com.shixi3.communitybackend.examine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.examine.entity.CarVet;
import com.shixi3.communitybackend.examine.vo.CarVetVo;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CarVetMapper extends BaseMapper<CarVet> {
    List<CarVetVo> getCarVetByStatus(Integer status);

    List<CarVetVo> getCarVetByUser(Long userId);
}
