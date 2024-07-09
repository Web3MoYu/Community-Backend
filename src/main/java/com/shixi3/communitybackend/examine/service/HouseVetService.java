package com.shixi3.communitybackend.examine.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;

public interface HouseVetService extends IService<TenantExamineRecord> {
    Page<TenantExamineRecord> page(Integer page,Integer pageSize,Integer type);
}
