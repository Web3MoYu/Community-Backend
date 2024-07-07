package com.shixi3.communitybackend.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi3.communitybackend.car.entity.Car;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CarMapper extends BaseMapper<Car> {

    List<Car> getAllCar();

    @Delete("delete from car where owner = #{owner}")
    void deleteByOwner(Long owner);

    Car getCarById(Long carId);

    Car getCarByLicence(String licence);
}
