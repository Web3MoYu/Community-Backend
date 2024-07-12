package com.shixi3.communitybackend.building.vo;

import com.shixi3.communitybackend.building.entity.Building;
import lombok.Data;

@Data
public class BuildingVo extends Building {
    private Integer userNum;
}
