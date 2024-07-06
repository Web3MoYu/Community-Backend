package com.shixi3.communitybackend.Family.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.Family.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    private WxUserMapper wxUserMapper;
    @Override
    public WxUser getWxUserByIdCard(String IdCard) {
        LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getIdCard, IdCard);
        return wxUserMapper.selectOne(queryWrapper);
    }

    @Override
    public WxUser getWxUserById(Long id) {
        LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getId, id);
        return wxUserMapper.selectOne(queryWrapper);
    }
}
