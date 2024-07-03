package com.shixi3.communitybackend.car.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("parking")
public class Parking {
    /**
     * 车位id
     */
    @TableId
    private Long parkingId;
    /**
     * 车位编号
     */
    private String number;
    /**
     * 车位状态
     */
    private Integer status;
    /**
     * 车位拥有者
     */
    private Long owner;
}
