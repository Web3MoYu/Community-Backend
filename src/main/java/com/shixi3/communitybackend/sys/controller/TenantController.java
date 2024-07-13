package com.shixi3.communitybackend.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.Family.vo.WxUserVo;
import com.shixi3.communitybackend.car.service.CarService;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.sys.entity.Tenant;
import com.shixi3.communitybackend.sys.service.TenantService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/tenant")
public class TenantController {

    @Resource
    private TenantService tenantService;

    @Resource
    private CarService carService;

    @Resource
    private WxUserMapper wxUserMapper;

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
        wrapper.like(name != null, Tenant::getName, name);
        Page<Tenant> result = tenantService.page(new Page<>(page, pageSize), wrapper);
        return CommonResult.success(result);
    }


    /**
     * 删除用户
     * @param id
     * @param parentId
     * @param userType
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:wxUser:delete')")
    public CommonResult<String> deleteWxUser(@RequestParam Long id,@RequestParam Long parentId,@RequestParam Integer userType){
        if(userType == 0){        //是户主
            List<Long> list = new ArrayList<>();
            //拿到与当前parentId有关的所有人id
            List<WxUserVo> wxUserVo = wxUserMapper.getGroupsByParent(parentId, "");
            WxUserVo wxUserVo2 = new WxUserVo();
            wxUserVo2.setId(id);
            wxUserVo.add(wxUserVo2);

            for(WxUserVo wxUserVo1 : wxUserVo){
                //删除car
                carService.deleteByOwner(wxUserVo1.getId());

                //删除user_house表中对应数据
                tenantService.deleteUserHouse(wxUserVo1.getId());
                //将用户改为游客
                tenantService.changeUser(wxUserVo1.getId());
            }
        }else if(userType == 3){
            tenantService.deleteWxUser(id, userType);
        }
        else {  //业主（户主家人）或 租户
            //删除car
            carService.deleteByOwner(id);
            //删除user_house表中对应数据
            tenantService.deleteUserHouse(id);
            //将用户改为游客
            tenantService.changeUser(id);
        }
//        else if(userType == 2){  //租户
//            //删除car
//            carService.deleteByOwner(id);
//            //删除user_house表中对应数据
//            tenantService.deleteUserHouse(id);
//
//            //将用户改为游客
//            tenantService.changeUser(id);
//        }
        //改变house
        tenantService.deleteWxUser( id, userType);

        return CommonResult.success("删除成功");
    }

    /**
     * 修改微信用户的电话号码、身份证号、性别、昵称
     * @param tenant
     * @return
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('sys:wxUser:edit')")
    public CommonResult<String> updateHouse(@RequestBody Tenant tenant) {
        System.out.println(tenant.getIdCard());
        boolean update = tenantService.updateById(tenant);
        if(update) {
            return CommonResult.success("修改用户信息成功！");
        }
        return CommonResult.error(500,"修改用户信息失败！");
    }

    /**
     * 查找单个用户数据用于回显信息
     * @param id
     * @return
     */
    @GetMapping("/getOne/{id}")
    public CommonResult<Tenant> getOneById(@PathVariable Long id){
        Tenant tenant = tenantService.getOneById(id);
        return CommonResult.success(tenant);
    }


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
