package com.shixi3.communitybackend.examine.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("tenant_examine_record")
@Data
public class TenantExamineRecord implements Serializable {
    @TableId
    private Long id;

    private Long wxUserId;

    private Integer userType;

    private String certImg;

    private Integer status;

    private Long houseId;

    private Date createTime;

    private Date updateTime;

    private String notes;
}
