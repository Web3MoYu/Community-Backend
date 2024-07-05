package com.shixi3.communitybackend.Family.service;

import com.shixi3.communitybackend.Family.entity.WxUser;

public interface WxUserService {
    /**
     * 根据身份证获取微信用户
     * @param IdCard
     * @return
     */
    WxUser getWxUserByIdCard(String IdCard);
}
