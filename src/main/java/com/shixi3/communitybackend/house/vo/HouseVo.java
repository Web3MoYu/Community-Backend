package com.shixi3.communitybackend.house.vo;

import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.house.entity.House;
import lombok.Data;

import java.util.List;

@Data
public class HouseVo extends House {
    /**
     * 户主姓名
     */
    private String ownerName;
    /**
     * 楼栋编号
     */
    private Long buildingNumber;

    private String ownerCard;

    private List<WxUser> tenants;
}
