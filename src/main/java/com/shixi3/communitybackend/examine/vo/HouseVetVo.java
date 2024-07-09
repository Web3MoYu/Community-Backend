package com.shixi3.communitybackend.examine.vo;

import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;
import lombok.Data;

@Data
public class HouseVetVo extends TenantExamineRecord {
    // 用户信息
    private String name;

    private String idCard;

    private String phone;

    private Integer sex;

    // 楼栋
    private Long buildingNumber;

    private String houseNumber;
}
