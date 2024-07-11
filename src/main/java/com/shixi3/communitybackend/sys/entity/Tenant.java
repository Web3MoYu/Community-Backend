package com.shixi3.communitybackend.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wx_user")
public class Tenant {

    @TableId
    private Long id;

    private String wxId;

    private String phone;

    private String name;

    private Integer sex;  //性别0-男  1-女

    private String idCard;

    private String avatar;  //头像

    private String WxUser;

    private Date createTime;

    private Date updateTime;

    private Integer userType;   //0-业主  1-租户  2-游客

    private Long parentId;

    private String nickname;
}
