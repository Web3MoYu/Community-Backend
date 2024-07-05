package com.shixi3.communitybackend.Family.controller;

import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.mapper.UserHouseMapper;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.Family.vo.DeleteVo;
import com.shixi3.communitybackend.auth.mapper.UserMapper;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Family")
@Slf4j
public class UserHouseController {
    @Resource
    UserHouseService userHouseService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserHouseMapper userHouseMapper;

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

    /**
     *
     * 为空房添加户主（需先验证为空或提取所有空房）
     * @param houseId
     * @param wxUserId
     * @return
     */
    @PostMapping("/addHouseHold")
    public CommonResult addHouseHold(Long houseId, Long wxUserId) {
        UserHouse userHouse=new UserHouse();
        userHouse.setHouseId(houseId);
        userHouse.setWxUserId(wxUserId);
        long count=userHouseService.addHouseHold(userHouse);
        if(count!=0){
            return CommonResult.success(count);
        }else {
            return CommonResult.error(0,"添加失败");
        }
    }

    /**
     * 获取房屋所有成员（除开户主)
     *
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
     * 前提：用户为户主
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

    /**
     * 删除用户房屋关系表的一条数据
     * 在前台户主先查询家庭成员后可删除家庭成员（先判断是否为户主）
     * 后台管理员可执行删除户主和该房屋成员
     * @param id
     * @return
     */
    @DeleteMapping("/deleteHouseMember/{id}")
    public CommonResult<Integer> deleteHouseMember(@PathVariable Long id) {
        int count=userHouseService.deleteHouseMember(id);
        if(count!=0){
            return CommonResult.success(count);
        }else {
            throw new BizException("删除失败!");
        }
    }

    /**
     * 批量删除用户房屋关系
     * 后台管理员可删除户主，成员，租户
     * 前台户主可删除家庭成员
     * @param
     * @return
     */
    @DeleteMapping("/batchDeleteHouseMember")
    public CommonResult<String> batchDeleteHouseMember(@RequestBody DeleteVo deleteVo) {
        int count=userHouseMapper.deleteBatchIds(deleteVo.getIds());
        if(count!=0){
            return CommonResult.success("删除成功");
        }else {
            throw new BizException("删除失败");
        }
    }
}
