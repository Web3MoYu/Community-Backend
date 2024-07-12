package com.shixi3.communitybackend.examine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("car_vet")
public class CarVet {
    /**
     * 审核id
     */
    @TableId(value = "vet_id",type = IdType.AUTO)
    private Long vetId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 车牌号
     */
    private String carLicence;
    /**
     * 车型
     */
    private String carType;
    /**
     * 审核状态
     */
    private Integer vetStatus;
    /**
     * 审核理由
     */
    private String vetReason;

    private Date createTime;

    private Date updateTime;
}
