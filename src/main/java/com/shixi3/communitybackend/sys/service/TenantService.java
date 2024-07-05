package com.shixi3.communitybackend.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.common.entity.User;
import com.shixi3.communitybackend.sys.entity.Tenant;

import java.util.List;


public interface TenantService extends IService<Tenant> {

    List<Tenant> getIdCardByName(String name);

    List<String> getAllName();

    Tenant getUserByIdCard(String idCard);

}
