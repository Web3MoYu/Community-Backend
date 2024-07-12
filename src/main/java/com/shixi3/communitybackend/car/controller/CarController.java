package com.shixi3.communitybackend.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.car.Vo.CarVo;
import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.service.CarService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarController {
    @Resource
    private CarService carService;

    /**
     * 统计数量
     *
     * @return
     */
    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<Long> count() {
        long count = carService.count();
        return CommonResult.success(count);
    }

    /**
     * 查看个人车辆信息
     *
     * @param owner 个人ID
     * @return 车辆列表
     */
    @GetMapping("/list/{owner}")
    public CommonResult<List<Car>> getCarByOwner(@PathVariable Long owner) {
        LambdaQueryWrapper<Car> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Car::getOwner, owner);
        List<Car> cars = carService.list(wrapper);
        if (!cars.isEmpty()) {
            return CommonResult.success(cars);
        } else {
            return CommonResult.error(1, "车辆信息获取失败!");
        }
    }

    /**
     * 查看所有车辆信息
     *
     * @return 车辆列表
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('car:cars:list')")
    public CommonResult<List<CarVo>> getAllCar() {
        List<CarVo> cars = carService.getAllCar();
        if (cars != null) {
            return CommonResult.success(cars);
        } else {
            return CommonResult.error(1, "车辆信息获取失败!");
        }
    }

    /**
     * 新增车辆信息
     *
     * @param car 提交车辆信息
     * @return 提示信息
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('car:cars:add')")
    public CommonResult<String> addCar(@RequestBody Car car) {
        boolean save = carService.save(car);
        if (save) {
            return CommonResult.success("新增车辆成功！");
        }
        return CommonResult.error(500, "新增车辆失败！");
    }

    /**
     * 查询车牌号对应车辆
     *
     * @param licence 车牌号
     * @return 提示信息
     */
    @GetMapping("/exist/{licence}")
    public CommonResult<Boolean> carExists(@PathVariable String licence) {
        LambdaQueryWrapper<Car> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Car::getLicence).eq(Car::getLicence, licence);
        boolean exists = carService.getOne(wrapper) != null;
        return CommonResult.success(exists);
    }

    /**
     * 根据id删除车辆信息
     *
     * @param carId 车辆id
     * @return 提示信息
     */
    @DeleteMapping("/delete/{carId}")
    @PreAuthorize("hasAnyAuthority('car:cars:delete')")
    public CommonResult<String> deleteCar(@PathVariable Long carId) {
        LambdaQueryWrapper<Car> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Car::getCarId, carId);
        boolean delete = carService.remove(wrapper);
        if (delete) {
            return CommonResult.success("删除车辆成功!");
        }
        return CommonResult.error(500, "删除车辆失败!");
    }

    /**
     * 编辑车辆信息
     *
     * @param car 编辑的车辆信息
     * @return 提示信息
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('car:cars:edit')")
    public CommonResult<String> updateParking(@RequestBody Car car) {
        boolean update = carService.updateById(car);
        if (update) {
            return CommonResult.success("修改车辆信息成功！");
        }
        return CommonResult.error(500, "修改车辆信息失败！");
    }


    /**
     * 根据id查看车辆信息
     *
     * @param id 车辆id
     * @return 车辆信息
     */
    @GetMapping("/getOneById/{id}")
    @PreAuthorize("hasAuthority('car:cars:edit')")
    public CommonResult<CarVo> getCarById(@PathVariable Long id) {
        CarVo car = carService.getCarById(id);
        return CommonResult.success(car);
    }

    /**
     * 根据车牌号查询车辆信息
     *
     * @param licence 车牌号
     * @return 车辆信息
     */
    @GetMapping("/search/{licence}")
    @PreAuthorize("hasAuthority('car:cars:list')")
    public CommonResult<List<CarVo>> getCarByLicence(@PathVariable String licence) {
        List<CarVo> cars = carService.getCarByLicence(licence);
        return CommonResult.success(cars);
    }
}
