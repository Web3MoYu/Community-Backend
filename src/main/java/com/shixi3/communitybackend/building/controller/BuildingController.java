package com.shixi3.communitybackend.building.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.building.entity.Building;
import com.shixi3.communitybackend.building.mapper.BuildingMapper;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingController {
    @Resource
    private BuildingMapper buildingMapper;

    @GetMapping("/building/page")
    public CommonResult<Page<Building>> page(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "5") Integer pageSize,
                                             @RequestParam(required = false) String buildingName) {
        Page<Building> result = new Page<>(page,pageSize);
        result = buildingMapper.page(result,buildingName);
        return CommonResult.success(result);
    }
}
