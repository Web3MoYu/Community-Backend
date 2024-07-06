package com.shixi3.communitybackend.Family.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.Family.entity.WxUser;

public interface WxUserService extends IService<WxUser> {
    /**
     * 根据身份证获取微信用户
     * @param IdCard
     * @return
     */
    WxUser getWxUserByIdCard(String IdCard);

    /**
     * 根据微信用户表主键获取
     * @param id
     * @return
     */
    WxUser getWxUserById(Long id);
}
