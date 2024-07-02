package com.shixi3.communitybackend.car.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("car")
public class Car {
    /**
     * 车辆id
     */
    @TableId
    private Long carId;
    /**
     * 拥有者id
     */
    private Long owner;
    /**
     * 车牌号
     */
    private String licence;
    /**
     * 车辆型号
     */
    private String type;
}
