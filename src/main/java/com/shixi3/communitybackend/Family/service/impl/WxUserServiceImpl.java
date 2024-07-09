package com.shixi3.communitybackend.Family.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper,WxUser> implements WxUserService {
    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 身份证获取微信用户
     * @param IdCard
     * @return
     */
    @Override
    public WxUser getWxUserByIdCard(String IdCard) {
        LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getIdCard, IdCard);
        return wxUserMapper.selectOne(queryWrapper);
    }

    /**
     * 微信用户表主键获取微信用户
     * @param id
     * @return
     */
    @Override
    public WxUser getWxUserById(Long id) {
        LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getId, id);
        return wxUserMapper.selectOne(queryWrapper);
    }

}
