package com.shixi3.communitybackend.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.auth.mapper.UserMapper;
import com.shixi3.communitybackend.auth.service.UserService;
import com.shixi3.communitybackend.common.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
}
