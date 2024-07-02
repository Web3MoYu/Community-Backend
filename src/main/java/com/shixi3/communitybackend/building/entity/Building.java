package com.shixi3.communitybackend.building.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("building")
public class Building implements Serializable {

    /**
    * 楼栋id
    */
    private Long buildingId;
    /**
    * 楼栋编号
    */
    private String buildingNumber;
    /**
    * 楼层数
    */
    private Long floorNum;
    /**
    * 房屋数
    */
    private Long houseNum;

    private Date createTime;

    private Date updateTime;

}
