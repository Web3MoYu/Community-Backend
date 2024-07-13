package com.shixi3.communitybackend.Family.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyHouseVo {
    private Long houseId;
    private String houseNumber;
    private Long buildingNumber;
}
