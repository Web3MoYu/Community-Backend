package com.shixi3.communitybackend.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.sys.entity.MenuRole;
import com.shixi3.communitybackend.sys.mapper.MenRoleMapper;
import com.shixi3.communitybackend.sys.service.MenuRoleService;
import org.springframework.stereotype.Service;

@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenRoleMapper, MenuRole>
        implements MenuRoleService {
}
