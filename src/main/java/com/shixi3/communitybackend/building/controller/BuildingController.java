package com.shixi3.communitybackend.building.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.building.entity.Building;
import com.shixi3.communitybackend.building.service.BuildingService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/building")
public class BuildingController {
    @Resource
    private BuildingService buildingService;

    /**
     * 楼栋分页查询
     * @param page 当前页
     * @param pageSize 页面大小
     * @param buildingName 楼栋名称
     * @return 分页信息
     */
    @GetMapping("/list")
    public CommonResult<Page<Building>> page(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "5") Integer pageSize,
                                             @RequestParam(required = false) String buildingName) {
        Page<Building> result = buildingService.page(page,pageSize,buildingName);
        return CommonResult.success(result);
    }

    /**
     * 新增楼栋信息
     * @param building 包含楼栋信息的实体
     * @return 提示信息
     */
    @PostMapping("/add")
    public CommonResult<String> addBuilding(@RequestBody Building building) {
        boolean save = buildingService.save(building);
        if(save) {
            return CommonResult.success("新增楼栋成功！");
        }
        return CommonResult.error(500,"新增楼栋失败！");

    }
}
