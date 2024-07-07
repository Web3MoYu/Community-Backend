package com.shixi3.communitybackend.wxapp.service;

import com.shixi3.communitybackend.wxapp.DTO.WechatLoginRequestDTO;
import com.shixi3.communitybackend.wxapp.vo.WxUserLoginVo;

public interface WechatService {
    WxUserLoginVo getToken(WechatLoginRequestDTO loginRequest) throws Exception;
}