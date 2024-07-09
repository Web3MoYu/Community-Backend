package com.shixi3.communitybackend.wxapp.vo;

import com.shixi3.communitybackend.Family.entity.WxUser;
import lombok.Data;

@Data
public class WxUserLoginVo {
    WxUser user;
    String token;

}
