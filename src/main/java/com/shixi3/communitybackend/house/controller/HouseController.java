package com.shixi3.communitybackend.house.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.service.HouseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Resource
    private HouseService houseService;

    /**
     *
     * @param page 当前页数
     * @param pageSize 页面大小
     * @param houseNumber 房号
     * @return 分页信息
     */
    @GetMapping("/list")
    public CommonResult<Page<House>> page(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                          @RequestParam(required = false) String houseNumber) {
        System.out.println("jflds");
        Page<House> result = houseService.page(page,pageSize,houseNumber);
        return CommonResult.success(result);
    }

    /**
     * 添加房屋信息
     * @param house 房屋信息
     * @return 提示信息
     */
    @PostMapping("/add")
    public CommonResult<String> addHouse(@RequestBody House house) {
        boolean save = houseService.save(house);
        if(save) {
            return CommonResult.success("添加房屋信息成功！");
        }
        return CommonResult.error(500,"添加房屋信息失败！");
    }

    /**
     * 修改房屋信息
     * @param house 房屋信息
     * @return 提示信息
     */
    @PutMapping("/edit")
    public CommonResult<String> updateHouse(@RequestBody House house) {
        boolean update = houseService.updateById(house);
        if(update) {
            return CommonResult.success("修改房屋信息成功！");
        }
        return CommonResult.error(500,"修改房屋信息失败！");
    }

    /**
     * 根据id获取房屋信息
     * @param id 房屋id
     * @return 房屋信息
     */
    @GetMapping("/getOne/{id}")
    public CommonResult<House> getHouse(@PathVariable Long id) {
        House house = houseService.getById(id);
        return CommonResult.success(house);
    }

    /**
     * 删除房屋信息
     * @param id 房屋id
     * @return 提示信息
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult<String> deleteHouse(@PathVariable Long id) {
        boolean delete = houseService.removeById(id);
        if(delete) {
            return CommonResult.success("删除房屋信息成功！");
        }
        return CommonResult.error(500,"删除房屋信息失败！");
    }

    /**
     * 批量删除房屋信息
     * @param ids 房屋id列表
     * @return 提示信息
     */
    @DeleteMapping("/delBatch")
    public CommonResult<String> delBatch(@RequestBody List<Long> ids) {
        boolean delete = houseService.removeBatchByIds(ids);
        if(delete) {
            return CommonResult.success("批量删除房屋信息成功！");
        }
        return CommonResult.error(500,"批量删除房屋信息失败！");
    }
}