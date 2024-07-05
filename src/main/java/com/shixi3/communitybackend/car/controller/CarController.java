package com.shixi3.communitybackend.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.building.entity.Building;
import com.shixi3.communitybackend.car.entity.Car;
import com.shixi3.communitybackend.car.entity.Parking;
import com.shixi3.communitybackend.car.service.CarService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarController {
    @Resource
    private CarService carService;

    /**
     * 查看个人车辆信息
     * @param owner 个人ID
     * @return
     */
    @GetMapping("/list/{owner}")
    public CommonResult<List<Car>> getCarById(@PathVariable Long owner) {
        LambdaQueryWrapper<Car> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Car::getOwner,owner);
        List<Car> cars = carService.list(wrapper);
        if (!cars.isEmpty()) {
            return CommonResult.success(cars);
        } else {
            return CommonResult.error(1,"车辆信息获取失败!");
        }
    }

    /**
     * 查看所有车辆信息
     * @return
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('car:cars:list')")
    public CommonResult<List<Car>> getAllCar(){
        List<Car> cars=carService.getAllCar();
        if (cars!=null) {
            return CommonResult.success(cars);
        }else {
            return CommonResult.error(1,"车辆信息获取失败!");
        }
    }
    /**
     * 新增车辆信息
     * @param car 提交车辆信息
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('car:cars:add')")
    public CommonResult<String> addCar(@RequestBody Car car){
        boolean save=carService.save(car);
        if(save) {
            return CommonResult.success("新增车辆成功！");
        }
        return CommonResult.error(500,"新增车辆失败！");
    }

    /**
     * 查询车牌号对应车辆
     * @param licence 车牌号
     * @return
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
     * @param id 车辆id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('car:cars:delete')")
    public CommonResult<String> deleteCar(@PathVariable Long id){
        boolean delete=carService.removeById(id);
        if(delete){
            return CommonResult.success("删除车辆成功!");
        }
        return CommonResult.error(500,"删除车辆失败!");
    }

    /**
     * 编辑车辆信息
     * @param car 编辑的车辆信息
     * @return
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('car:cars:edit')")
    public CommonResult<String> updateParking(@RequestBody Car car) {
        boolean update = carService.updateById(car);
        if(update) {
            return CommonResult.success("修改车辆信息成功！");
        }
        return CommonResult.error(500,"修改车辆信息失败！");
    }


}
