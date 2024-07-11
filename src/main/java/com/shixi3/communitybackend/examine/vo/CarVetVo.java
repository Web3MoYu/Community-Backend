package com.shixi3.communitybackend.examine.vo;

import com.shixi3.communitybackend.examine.entity.CarVet;
import lombok.Data;

@Data
public class CarVetVo extends CarVet {
    /**
     * 用户id
     */
    private Long userId;
}
