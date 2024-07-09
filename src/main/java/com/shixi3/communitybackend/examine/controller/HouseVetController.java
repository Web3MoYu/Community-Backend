package com.shixi3.communitybackend.examine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;
import com.shixi3.communitybackend.examine.service.HouseVetService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/houseVet")
public class HouseVetController {
    @Resource
    private HouseVetService houseVetService;

    @GetMapping("/list")
    public CommonResult<Page<TenantExamineRecord>> page(@RequestParam Integer page,
                                                        @RequestParam Integer pageSize,
                                                        @RequestParam(required = false) Integer type)
    {
        Page<TenantExamineRecord> result = houseVetService.page(page,pageSize,type);
        return CommonResult.success(result);
    }
}
