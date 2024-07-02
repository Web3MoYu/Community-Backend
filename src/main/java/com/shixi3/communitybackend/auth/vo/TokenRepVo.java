package com.shixi3.communitybackend.auth.vo;

import com.shixi3.communitybackend.common.entity.MenuTree;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TokenRepVo {
    private Long userId;

    private String username;

    private String phone;

    private String nickName;

    private Integer sex;

    private String avatar;

    private Date createTime;

    private Date updateTime;

    private List<MenuTree> menuTree;
}
