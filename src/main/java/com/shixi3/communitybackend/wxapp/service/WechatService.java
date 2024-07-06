package com.shixi3.communitybackend.wxapp.service;

import com.shixi3.communitybackend.wxapp.DTO.WechatLoginRequestDTO;

public interface WechatService {
    String getToken(WechatLoginRequestDTO loginRequest) throws Exception;
 
    void checkToken(String token) throws Exception;
}