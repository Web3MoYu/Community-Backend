package com.shixi3.communitybackend.car.Vo;

import com.shixi3.communitybackend.car.entity.Car;
import lombok.Data;

@Data
public class CarVo extends Car {
    private String name;

    private String idCard;
}
