package com.shixi3.communitybackend.examine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("parking_vet")
public class ParkingVet {
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
     * 车位号
     */
    private String parkingNumber;
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
