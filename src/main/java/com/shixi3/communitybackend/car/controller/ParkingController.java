package com.shixi3.communitybackend.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.car.Vo.ParkingVo;
import com.shixi3.communitybackend.car.entity.Parking;
import com.shixi3.communitybackend.car.service.ParkingService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parking")
public class ParkingController {
    @Resource
    private ParkingService parkingService;

    /**
     * 统计数量
     *
     * @return
     */
    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<Long> count() {
        long count = parkingService.count();
        return CommonResult.success(count);
    }

    /**
     * 查询个人车位
     *
     * @param owner 用户id
     * @return 车位列表
     */
    @GetMapping("/list/{owner}")
    public CommonResult<List<Parking>> getParkingByOwner(@PathVariable Long owner) {
        LambdaQueryWrapper<Parking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Parking::getOwner, owner);
        List<Parking> parkings = parkingService.list(wrapper);
        if (!parkings.isEmpty()) {
            return CommonResult.success(parkings);
        } else {
            return CommonResult.error(1, "车位信息获取失败!");
        }
    }

    /**
     * 查询所有车位
     *
     * @return 车位列表
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('car:parking:list')")
    public CommonResult<List<ParkingVo>> getAllParking() {
        List<ParkingVo> parkings = parkingService.getAllParking();
        if (!parkings.isEmpty()) {
            return CommonResult.success(parkings);
        } else {
            return CommonResult.error(1, "车位信息获取失败!");
        }
    }

    /**
     * 新增车位信息
     *
     * @param parking 提交车位信息
     * @return 提示信息
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('car:parking:add')")
    public CommonResult<String> addParking(@RequestBody Parking parking) {
        boolean save = parkingService.save(parking);
        if (save) {
            return CommonResult.success("新增车位成功！");
        }
        return CommonResult.error(500, "新增车位失败！");
    }

    /**
     * 根据id删除车位信息
     *
     * @param id 车位id
     * @return 提示信息
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('car:parking:delete')")
    public CommonResult<String> deleteParking(@PathVariable Long id) {
        boolean delete = parkingService.removeById(id);
        if (delete) {
            return CommonResult.success("删除车位成功!");
        }
        return CommonResult.error(500, "删除车位失败!");
    }

    /**
     * 编辑车位信息
     *
     * @param parking 编辑的车位信息
     * @return 提示信息
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('car:parking:edit')")
    public CommonResult<String> updateParking(@RequestBody Parking parking) {
        boolean update = parkingService.updateById(parking);
        if (update) {
            return CommonResult.success("修改车位信息成功！");
        }
        return CommonResult.error(500, "修改车位信息失败！");
    }

    /**
     * 根据车位编号获取审核的车位信息
     *
     * @param number 车位编号
     * @return 车位列表
     */
    @GetMapping("/getParkingByNumber/{number}")
    public CommonResult<List<ParkingVo>> getParkingByNumber(@PathVariable String number) {
        List<ParkingVo> parkings = parkingService.getParkingByNumber(number);
        return CommonResult.success(parkings);
    }

    /**
     * 根据id查看车位信息
     *
     * @param id 车位id
     * @return 车位信息
     */
    @GetMapping("/getOne/{id}")
    @PreAuthorize("hasAuthority('car:parking:edit')")
    public CommonResult<Parking> getParkingById(@PathVariable Long id) {
        Parking parking = parkingService.getParkingById(id);
        return CommonResult.success(parking);
    }

}
