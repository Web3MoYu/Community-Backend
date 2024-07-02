package com.shixi3.communitybackend.sys.vo;

import com.shixi3.communitybackend.common.entity.User;
import lombok.Data;

@Data
public class UserVo extends User {
    private Long roleId;

    private boolean resetPassword;
}