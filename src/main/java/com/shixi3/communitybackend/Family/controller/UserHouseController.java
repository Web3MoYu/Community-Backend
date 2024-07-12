package com.shixi3.communitybackend.Family.controller;

import com.shixi3.communitybackend.Family.entity.UserHouse;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.entity.WxUserTree;
import com.shixi3.communitybackend.Family.mapper.UserHouseMapper;
import com.shixi3.communitybackend.Family.service.UserHouseService;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.Family.vo.DeleteVo;
import com.shixi3.communitybackend.common.entity.Page;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.house.entity.House;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private UserHouseMapper userHouseMapper;
    @Autowired
    private WxUserService wxUserService;
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
    @PreAuthorize("hasAuthority('sys:wxUser:list')")
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
     * 获取房屋所有住户
     * @param houseId
     * @return
     */
    @GetMapping("/getUsersByHouseId/{houseId}")
    public CommonResult<List<WxUser>> getUsersByHouseId(@PathVariable Long houseId) {
        List<WxUser> users = userHouseService.getUsersByHouseId(houseId);
        if (users!=null) {
            return CommonResult.success(users);
        }else {
            throw new BizException("获取房屋所有用户失败!");
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
     * 获取房屋租户
     * @param houseId
     * @return
     */
    @GetMapping("/getTenantsByHouseId/{houseId}")
    public CommonResult<List<WxUser>> getTenantsByHouseId(@PathVariable Long houseId) {
        List<WxUser> tenants=userHouseService.getTenantsByHouseId(houseId);
        if (tenants!=null) {
            return CommonResult.success(tenants);
        }else {
            throw new BizException("获取租户失败!");
        }
    }

    /**
     * 添加家庭成员,租客
     * @param idCard
     * @param userHouse
     * @return
     */
    @PostMapping("/addHouseMemberTenant/{idCard}")
    public CommonResult<String> addHouseMember(@PathVariable String idCard, @RequestBody UserHouse userHouse) {
       WxUser wxUser=wxUserService.getWxUserByIdCard(idCard);
       if (wxUser==null) {
           return CommonResult.error(1,"用户未注册!");
       }else {
           List<Integer> relation=userHouseMapper.getUserHouseBelongFlag(wxUser.getId(),userHouse.getHouseId());
           if(relation.contains(userHouse.getBelongFlag())){
               return CommonResult.error(2,"用户已存在!");
           }else {
               UserHouse userHouse1=new UserHouse();
               userHouse1.setHouseId(userHouse.getHouseId());
               userHouse1.setWxUserId(wxUser.getId());
               userHouse1.setBelongFlag(userHouse.getBelongFlag());
               Long count=userHouseService.addHouseMemberTenant(userHouse1);
               if (count!=0) {
                   return CommonResult.success("添加成功!");
               }else {
                   throw new BizException("添加失败!");
               }
           }
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

    /**
     * 获取用户作为户主的房
     * @param wxUserId
     * @return
     */
    @GetMapping("/getMyHousesById/{wxUserId}")
    public CommonResult<List<House>> getMyHousesById(@PathVariable Long wxUserId) {
        List<House> myHouses=userHouseMapper.getMyHouses(wxUserId);
        if (myHouses!=null) {
            return CommonResult.success(myHouses);
        }else {
            throw new BizException("获取失败");
        }
    }

    /**
     * 获取用户所住的房
     * @param wxUserId
     * @return
     */
    @GetMapping("/getLiveHousesByUserId/{wxUserId}")
    public CommonResult<List<House>> getLiveHousesByUserId(@PathVariable Long wxUserId) {
        List<House> liveHouses=userHouseMapper.getLiveHouses(wxUserId);
        if (liveHouses!=null) {
            return CommonResult.success(liveHouses);
        }else {
            throw new BizException("获取失败!");
        }
    }

    /**
     * 获取房屋中用户的用户类型
     * @param userId
     * @param houseId
     * @return
     * @throws BizException
     */
    @GetMapping("/getUserHouseBelongFlag")
    public CommonResult<List<Integer>> getUserHouseBelongFlag(Long userId,Long houseId) throws BizException {
       return CommonResult.success(userHouseMapper.getUserHouseBelongFlag(userId,houseId));
    }
}
