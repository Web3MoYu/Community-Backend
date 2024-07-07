package com.shixi3.communitybackend.Family.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("wx_user")
public class WxUser {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String wxId;
    private String phone;
    private String name;
    private Integer sex;
    private String idCard;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer userType;
    private Long parentId;
}
