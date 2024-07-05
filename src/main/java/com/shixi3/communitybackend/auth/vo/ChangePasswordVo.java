package com.shixi3.communitybackend.auth.vo;

import lombok.Data;

@Data
public class ChangePasswordVo {
    private String oldPassword;
    private String newPassword;
}