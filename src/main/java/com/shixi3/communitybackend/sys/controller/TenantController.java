package com.shixi3.communitybackend.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.car.service.CarService;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.sys.entity.Tenant;
import com.shixi3.communitybackend.sys.service.TenantService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/tenant")
public class TenantController {

    @Resource
    private TenantService tenantService;

    @Resource
    private CarService carService;
    /**
     * 分页查询以及根据名字模糊查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
//    @PreAuthorize("hasAuthority('sys:role:list')")
    public CommonResult<Page<Tenant>> pageSearch(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize, @RequestParam(required = false) String name) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Tenant::getName, name)
                .ne(Tenant::getUserType, 2);
        Page<Tenant> result = tenantService.page(new Page<>(page, pageSize), wrapper);
        return CommonResult.success(result);
    }


    @DeleteMapping("/delete/{id}")
    public CommonResult<String> deleteById(@PathVariable Long id){

        //先删除表中他的车辆信息
        carService.deleteByOwner(id);

        //删除或修改对应房屋信息
        return CommonResult.success("删除成功");
    }

    //编辑


    /**
     * 获取所有人的姓名（去重后）
     * @return
     */
    @GetMapping("/getAllName")
    public CommonResult<List<String>> getAllName() {
        List<String> allName = tenantService.getAllName();
        return CommonResult.success(allName);
    }

    /**
     * 通过姓名查找身份证号list
     * @param name
     * @return
     */
    @GetMapping("/getIdCardByName/{name}")
    public CommonResult<List<Tenant>> getIdCardByName(@PathVariable String name) {
        List<Tenant> idCardByName = tenantService.getIdCardByName(name);
        return CommonResult.success(idCardByName);
    }

    /**
     * 通过身份证号查用户信息
     * @param idCard
     * @return
     */
    @GetMapping("/getUserByIdCard/{idCard}")
    public CommonResult<Tenant> getUserByIdCard(@PathVariable String idCard) {
        Tenant tenant = tenantService.getUserByIdCard(idCard);
        return CommonResult.success(tenant);
    }
}
