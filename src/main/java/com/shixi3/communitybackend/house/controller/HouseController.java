package com.shixi3.communitybackend.house.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.service.HouseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
}
