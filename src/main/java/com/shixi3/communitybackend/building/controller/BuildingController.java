package com.shixi3.communitybackend.building.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.building.entity.Building;
import com.shixi3.communitybackend.building.service.BuildingService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


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

    /**
     * 删除楼栋信息
     * @param id 楼栋id
     * @return 提示信息
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult<String> deleteBuilding(@PathVariable Long id) {
        boolean delete = buildingService.removeById(id);
        if(delete) {
            return CommonResult.success("删除楼栋成功！");
        }
        return CommonResult.error(500,"删除楼栋失败！");
    }

    /**
     * 校验楼栋编号是否重复
     * @param number 楼栋编号
     * @return 楼栋查询信息
     */
    @GetMapping("/check/{number}")
    public CommonResult<Boolean> checkBuildingNumber(@PathVariable Long number) {
        LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(number != null,Building::getBuildingNumber,number);
        Building building = buildingService.getOne(wrapper);
        return CommonResult.success(Objects.isNull(building));
    }
}
