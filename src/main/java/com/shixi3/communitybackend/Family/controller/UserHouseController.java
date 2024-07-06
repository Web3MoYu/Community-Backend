package com.shixi3.communitybackend.Family.controller;

import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.entity.WxUserTree;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.common.entity.Page;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Family")
@Slf4j
public class UserHouseController {
    @Resource
    UserHouseService userHouseService;

    /**
     * 获取房屋户主
     * @param id
     * @return
     */
    @GetMapping("/HouseHold/{id}")
    public CommonResult<WxUser> getHouseHoldByHouseId(@PathVariable Long id) {
        WxUser household=userHouseService.getHouseholdByHouseId(id);
        if (household!=null) {
            return CommonResult.success(household);
        }else {
            return CommonResult.error(1,"获取房屋户主失败!");
        }
    }

    @GetMapping("/page")
    public CommonResult<Page<WxUserTree>> getList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, @RequestParam(value = "name", required = false) String name){

        List<WxUserTree> info = userHouseService.selectWxUser(page, pageSize, (name == null ? "" : name));
        Integer index = (page - 1) * pageSize ;
        List<WxUserTree> pageInfo = new ArrayList<>();
        //手动分页
        if(index > info.size()){
            index = 0;
        }
        Integer last = index + pageSize > info.size() ? info.size() : index + pageSize;
        System.out.println(index + "  " + last);
        for(; index < last; index++){
            pageInfo.add(info.get(index));
        }
        //对page进行赋值
        Page<WxUserTree> page1 = new Page<>();
        page1.setPage(page);
        page1.setPageSize(pageSize);
        page1.setTotal(info.size());
        page1.setRecords(pageInfo);
        return CommonResult.success(page1);
    }

    /**
     *
     * 为空房添加户主
     * @param houseId
     * @param userId
     * @return
     */
    @PostMapping("/addHouseHold")
    public CommonResult addHouseHold(Long houseId, Long userId) {
        return null;
    }

    /**
     * 过户
     * @param houseId
     * @param wxUserId
     * @return
     */
    @PutMapping("/updateHouseHold")
    public CommonResult<String> updateHouseHold(Long houseId, Long wxUserId) {
        UserHouse userHouse=new UserHouse();
        userHouse.setHouseId(houseId);
        userHouse.setWxUserId(wxUserId);
        if (userHouseService.updateHouseHold(houseId,wxUserId)) {
            return CommonResult.success("过户成功!");
        }else {
            throw new BizException("过户失败!");
        }
    }

    /**
     * 获取房屋所有成员（除开户主)
     * @param houseId
     * @return
     */
    @GetMapping("/HouseMembers/{houseId}")
    public CommonResult<List<WxUser>> getHouseMembersByHouseId(@PathVariable Long houseId) {
        List<WxUser> members=userHouseService.getHouseMembersByHouseId(houseId);
        if (members!=null) {
            return CommonResult.success(members);
        }else {
            return CommonResult.error(0,"获取房屋所有成员失败!");
        }
    }

    /**
     * 添加家庭成员
     * @param houseId
     * @param wxUserId
     * @return
     */
    @PostMapping("/addHouseMember")
    public CommonResult addHouseMember(Long houseId,Long wxUserId) {
        UserHouse userHouse=new UserHouse();
        userHouse.setHouseId(houseId);
        userHouse.setWxUserId(wxUserId);
        long count=userHouseService.addHouseMember(userHouse);
        if(count!=0){
            return CommonResult.success(count);
        }else {
            return CommonResult.error(0,"添加失败");
        }
    }

}
