package com.shixi3.communitybackend.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role_menu")
public class MenuRole {

    @TableId
    private Long roleMenuId;

    private Long menuId;

    private Long roleId;
}
