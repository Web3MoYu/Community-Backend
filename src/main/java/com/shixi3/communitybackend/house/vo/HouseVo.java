package com.shixi3.communitybackend.house.vo;

import com.shixi3.communitybackend.house.entity.House;
import lombok.Data;

@Data
public class HouseVo extends House {
    /**
     * 户主姓名
     */
    private String ownerName;
    /**
     * 使用人姓名
     */
    private String tenantName;
    /**
     * 楼栋编号
     */
    private Long buildingNumber;
}
