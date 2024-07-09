package com.shixi3.communitybackend.examine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.examine.service.HouseVetService;
import com.shixi3.communitybackend.examine.vo.HouseVetVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/houseVet")
public class HouseVetController {
    @Resource
    private HouseVetService houseVetService;

    /**
     * 房屋审核信息分页
     * @param page 页数
     * @param pageSize 页面大小
     * @param status 审核状态
     * @return 分页信息
     */
    @GetMapping("/list")
    public CommonResult<Page<HouseVetVo>> page(@RequestParam Integer page,
                                               @RequestParam Integer pageSize,
                                               @RequestParam(required = false) Integer status)
    {
        Page<HouseVetVo> result = houseVetService.page(page,pageSize,status);
        return CommonResult.success(result);
    }


    /**
     * 通过id获取审核信息
     * @param id 审核信息id
     * @return 审核信息
     */
    @GetMapping("/getOne/{id}")
    public CommonResult<HouseVetVo> getHouseVetVoById(@PathVariable Long id) {
        HouseVetVo houseVetVo = houseVetService.getHouseVetVoById(id);
        return CommonResult.success(houseVetVo);
    }
}
