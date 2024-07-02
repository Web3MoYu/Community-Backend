package com.shixi3.communitybackend.house.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("house")
public class House implements Serializable {

    /**
    * 房屋id
    */
    private Long houseId;
    /**
    * 户主id
    */
    private Long owner;
    /**
    * 楼栋
    */
    private Long building;
    /**
    * 房号
    */
    private String number;
    /**
    * 房屋状态 0:空闲 1:已售出 2:出租中
    */

    private Object state;
    /**
    * 使用人id
    */

    private Long user;

    private Date createTime;
    private Date updateTime;

}
