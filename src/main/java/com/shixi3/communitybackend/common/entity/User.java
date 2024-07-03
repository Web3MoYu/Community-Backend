package com.shixi3.communitybackend.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("sys_user")
@Data
public class User implements Serializable {

    @TableId
    private Long userId;

    private String username;

    private String password;

    private String salt;

    private String phone;

    private String name;

    private Integer sex;

    private String avatar;

    private Date createTime;

    private Date updateTime;
}