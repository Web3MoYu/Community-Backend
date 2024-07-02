package com.shixi3.communitybackend.Family.controller;

import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.common.entity.User;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param houseId
     * @return
     */
    @GetMapping("/HouseHold")
    public CommonResult<User> getHouseHoldByHouseId(Long houseId) {
        User household=userHouseService.getHouseholdByHouseId(houseId);
        if (household!=null) {
            return CommonResult.success(household);
        }else {
            return CommonResult.error(1,"获取房屋户主失败!");
        }
    }

    /**
     * 获取房屋所有成员（除开户主)
     * @param HouseId
     * @return
     */
    @GetMapping("/HouseMembers")
    public CommonResult<List> getHouseMembersByHouseId(Long HouseId) {
        List<User> members=userHouseService.getHouseMembersByHouseId(HouseId);
        if (members!=null) {
            return CommonResult.success(members);
        }else {
            return CommonResult.error(0,"获取房屋所有成员失败!");
        }
    }

}
