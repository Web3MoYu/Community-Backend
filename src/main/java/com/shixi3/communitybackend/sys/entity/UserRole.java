package com.shixi3.communitybackend.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user_role")
public class UserRole {

    Long userRoleId;

    Long userId;

    Long roleId;

}