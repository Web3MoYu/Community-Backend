package com.shixi3.communitybackend.Family.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHouse {
    private Long Id;//所属关系id
    private Long UserId;//用户id
    private Long HouseId;//房屋id
    private Integer BelongFlag;//所属关系，1表示用户为成员，0表示用户为户主
}
