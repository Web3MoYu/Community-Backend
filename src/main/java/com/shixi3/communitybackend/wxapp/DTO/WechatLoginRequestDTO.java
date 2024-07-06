package com.shixi3.communitybackend.wxapp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatLoginRequestDTO {
    private String code;  //微信code
}