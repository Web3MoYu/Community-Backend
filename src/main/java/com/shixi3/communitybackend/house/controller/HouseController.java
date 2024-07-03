package com.shixi3.communitybackend.house.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.service.HouseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
