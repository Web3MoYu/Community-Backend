package com.shixi3.communitybackend.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.sys.entity.Tenant;
import com.shixi3.communitybackend.sys.mapper.TenantMapper;
import com.shixi3.communitybackend.sys.service.TenantService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Resource
    private TenantMapper tenantMapper;

}