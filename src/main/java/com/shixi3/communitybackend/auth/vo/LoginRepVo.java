package com.shixi3.communitybackend.auth.vo;

import com.shixi3.communitybackend.common.entity.MenuTree;
import lombok.Data;

import java.util.List;

@Data
public class LoginRepVo {
    List<MenuTree> menuTree;
    String token;
}
