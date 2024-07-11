package com.shixi3.communitybackend.examine.vo;

import com.shixi3.communitybackend.examine.entity.ParkingVet;
import lombok.Data;

@Data
public class ParkingVetVo extends ParkingVet {
    /**
     * 用户名称
     */
    private String name;
}
