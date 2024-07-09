package com.shixi3.communitybackend.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.sys.entity.Tenant;
import com.shixi3.communitybackend.sys.mapper.TenantMapper;
import com.shixi3.communitybackend.sys.service.TenantService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Resource
    private TenantMapper tenantMapper;


    @Override
    public List<Tenant> getIdCardByName(String name) {
        List<Tenant> idCardByName = tenantMapper.getIdCardByName(name);
        return idCardByName;
    }

    @Override
    public List<String> getAllName() {
        List<String> names = tenantMapper.getAllName();
        return names;
    }

    public Tenant getUserByIdCard(String idCard){
        Tenant tenant = tenantMapper.getUserByIdCard(idCard);
        return tenant;
    }

    @Override
    public Tenant getOneById(Long id) {
        Tenant tenant = tenantMapper.getOneById(id);
        return tenant;
    }

    @Override
    public void deleteWxUser(Long id, Integer userType) {
        if(userType == 0){
            tenantMapper.changeHouse(id);
        }
    }

    @Override
    public void deleteUserHouse(Long wxUserId) {
        tenantMapper.deleteOne(wxUserId);
    }

    @Override
    public void changeUser(Long id) {
        tenantMapper.changeUser(id);
    }


}