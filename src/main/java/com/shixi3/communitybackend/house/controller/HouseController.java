package com.shixi3.communitybackend.house.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.house.entity.House;
import com.shixi3.communitybackend.house.service.HouseService;
import com.shixi3.communitybackend.house.vo.HouseVo;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Resource
    private HouseService houseService;

    /**
     * 统计数量
     *
     * @return
     */
    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<Long> count() {
        long count = houseService.count();
        return CommonResult.success(count);
    }

    /**
     * @param page       当前页数
     * @param pageSize   页面大小
     * @param buildingId 房号
     * @return 分页信息
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('house:house:list')")
    public CommonResult<Page<HouseVo>> page(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                            @RequestParam(required = false) Long buildingId) {
        Page<HouseVo> result = houseService.page(page, pageSize, buildingId);
        return CommonResult.success(result);
    }

    /**
     * 添加房屋信息
     *
     * @param house 房屋信息
     * @return 提示信息
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('house:house:add')")
    public CommonResult<String> addHouse(@RequestBody HouseVo house) {
        boolean save = houseService.save(house);
        houseService.saveWithUserHouseAndTopType(house);
        if (save) {
            return CommonResult.success("添加房屋信息成功！");
        }
        return CommonResult.error(500, "添加房屋信息失败！");
    }

    /**
     * 修改房屋信息
     *
     * @param house 房屋信息
     * @return 提示信息
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('house:house:edit')")
    public CommonResult<String> updateHouse(@RequestBody HouseVo house) {
        boolean update = houseService.updateById(house);
        houseService.saveWithUserHouseAndTopType(house);
        if (update) {
            return CommonResult.success("修改房屋信息成功！");
        }
        return CommonResult.error(500, "修改房屋信息失败！");
    }

    /**
     * 根据id获取房屋信息
     *
     * @param id 房屋id
     * @return 房屋信息
     */
    @GetMapping("/getOne/{id}")
    @PreAuthorize("hasAuthority('house:house:edit')")
    public CommonResult<HouseVo> getHouse(@PathVariable Long id) {
        HouseVo house = houseService.getHouseVoById(id);
        return CommonResult.success(house);
    }

    /**
     * 删除房屋信息
     *
     * @param id 房屋id
     * @return 提示信息
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('house:house:delete')")
    public CommonResult<String> deleteHouse(@PathVariable Long id) {
        houseService.deleteWithUser(id);
        return CommonResult.success("删除房屋信息成功！");
    }

    /**
     * 批量删除房屋信息
     *
     * @param ids 房屋id列表
     * @return 提示信息
     */
    @DeleteMapping("/delBatch")
    @PreAuthorize("hasAuthority('house:house:delBatch')")
    public CommonResult<String> delBatch(@RequestBody List<Long> ids) {
        houseService.delBatchWithUser(ids);
        return CommonResult.success("批量删除房屋信息成功！");
    }

    /**
     * 检查房间编号是否重复
     *
     * @param number 房间编号
     * @return 校验信息
     */
    @GetMapping("/check/{number}")
    public CommonResult<Boolean> checkHouseNumber(@PathVariable String number) {
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(number != null, House::getHouseNumber, number);
        House house = houseService.getOne(wrapper);
        return CommonResult.success(Objects.isNull(house));
    }

    @GetMapping("/getRoom/{buildingId}")
//    @PreAuthorize("isAuthenticated()")
    public CommonResult<List<House>> getRoom(@PathVariable Long buildingId) {
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(buildingId != null, House::getBuildingId,buildingId);
        List<House> house = houseService.list(wrapper);
        System.out.println(house);
        return CommonResult.success(house);
    }
}