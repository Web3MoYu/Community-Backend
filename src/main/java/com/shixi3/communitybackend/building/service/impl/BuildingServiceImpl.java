package com.shixi3.communitybackend.building.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.building.entity.Building;
import com.shixi3.communitybackend.building.mapper.BuildingMapper;
import com.shixi3.communitybackend.building.service.BuildingService;
import com.shixi3.communitybackend.building.vo.BuildingVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service

public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {
    @Resource
    private BuildingMapper buildingMapper;

    /**
     * 楼栋分页查询
     * @param page 当前页
     * @param pageSize 页面大小
     * @param buildingNumber 楼栋名称
     * @return 分页信息
     */
    @Override
    public Page<BuildingVo> page(Integer page, Integer pageSize, Integer buildingNumber) {
        Page<BuildingVo> result = new Page<>(page,pageSize);
        result = buildingMapper.page(result,buildingNumber);
        return result;
    }
}
