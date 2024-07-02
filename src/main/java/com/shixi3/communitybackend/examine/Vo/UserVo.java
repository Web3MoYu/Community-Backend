package com.shixi3.communitybackend.examine.Vo;

import com.shixi3.communitybackend.common.entity.User;
import lombok.Data;

@Data
public class UserVo extends User {

    private String role;
}
