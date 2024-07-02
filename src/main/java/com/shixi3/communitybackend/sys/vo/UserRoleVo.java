package com.shixi3.communitybackend.sys.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleVo {

    private Long menuId;

    private Long parentId;

    private List<Long> permissions;
}