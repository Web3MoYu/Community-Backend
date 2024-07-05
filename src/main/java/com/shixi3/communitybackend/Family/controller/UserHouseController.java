package com.shixi3.communitybackend.Family.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.common.entity.User;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

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
    public CommonResult<User> getHouseHoldByHouseId(@PathVariable Long id) {
        User household=userHouseService.getHouseholdByHouseId(id);
        if (household!=null) {
            return CommonResult.success(household);
        }else {
            return CommonResult.error(1,"获取房屋户主失败!");
        }
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
     * 获取房屋所有成员（除开户主)
     * @param houseId
     * @return
     */
    @GetMapping("/HouseMembers/{houseId}")
    public CommonResult<List> getHouseMembersByHouseId(@PathVariable Long houseId) {
        List<User> members=userHouseService.getHouseMembersByHouseId(houseId);
        if (members!=null) {
            return CommonResult.success(members);
        }else {
            return CommonResult.error(0,"获取房屋所有成员失败!");
        }
    }

    /**
     * 添加家庭成员
     * @param houseId
     * @param userId
     * @return
     */
    @PostMapping("/addHouseMember")
    public CommonResult addHouseMember(Long houseId,Long userId) {
        UserHouse userHouse=new UserHouse();
        userHouse.setHouseId(houseId);
        userHouse.setWxUserId(userId);
        long count=userHouseService.addHouseMember(userHouse);
        if(count!=0){
            return CommonResult.success(count);
        }else {
            return CommonResult.error(0,"添加失败");
        }
    }

}
