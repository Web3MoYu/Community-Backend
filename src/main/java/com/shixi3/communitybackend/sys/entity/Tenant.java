package com.shixi3.communitybackend.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Tenant {

    @TableId
    private Long id;

    private String wxId;

    private String phone;

    private String name;

    private String sex;  //性别0-男  1-女

    private String idCard;

    private String avatar;  //头像

    private Date createTime;

    private Date updateTime;

    private int userType;   //0-业主  1-租户  2-游客
}
