package com.shixi3.communitybackend.examine.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;
import com.shixi3.communitybackend.examine.mapper.HouseVetMapper;
import com.shixi3.communitybackend.examine.service.HouseVetService;
import org.springframework.stereotype.Service;

@Service
public class HouseVetServiceImpl extends ServiceImpl<HouseVetMapper, TenantExamineRecord> implements HouseVetService {
    @Override
    public Page<TenantExamineRecord> page(Integer page, Integer pageSize, Integer type) {
        LambdaQueryWrapper<TenantExamineRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type != null,TenantExamineRecord::getUserType,type);
        Page<TenantExamineRecord> result = this.page(new Page<>(page,pageSize),wrapper);
        return result;
    }
}
