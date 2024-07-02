package com.shixi3.communitybackend.examine.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.common.entity.User;
import com.shixi3.communitybackend.examine.mapper.UserInformationMapper;
import com.shixi3.communitybackend.examine.service.UserInformationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserInformationServiceImpl extends ServiceImpl<UserInformationMapper, User> implements UserInformationService {

    @Resource
    private UserInformationMapper userInformationMapper;

}