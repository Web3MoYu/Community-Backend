package com.shixi3.communitybackend.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.sys.entity.Role;
import com.shixi3.communitybackend.sys.mapper.RoleMapper;
import com.shixi3.communitybackend.sys.service.RoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleService roleService;

    /**
     * 查询当前用户是什么角色
     *
     * @param id
     * @return
     */
    @GetMapping("/list/{id}")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public CommonResult<Role> getRoleById(@PathVariable("id") Long id) {
        Role role = roleMapper.getRoleByUserID(id);
        return CommonResult.success(role);
    }


    /**
     * 查询所有角色
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public CommonResult<List<Role>> list() {
        return CommonResult.success(roleService.list());
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public CommonResult<Page<Role>> pageSearch(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize, @RequestParam(required = false) String roleName) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(roleName != null, Role::getRoleName, roleName);
        Page<Role> result = roleService.page(new Page<>(page, pageSize), wrapper);
        return CommonResult.success(result);
    }

    /**
     * 编辑角色信息
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public CommonResult<String> updateRole(@RequestBody Role role, @PathVariable String id) {
        LambdaUpdateWrapper<Role> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(id != null, Role::getRoleId, id);
        boolean update = roleService.update(role, wrapper);
        if (!update) {
            throw new BizException("修改失败");
        }
        return CommonResult.success("修改成功");
    }

    /**
     * 添加角色信息
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
    public CommonResult<String> addRole(@RequestBody Role role) {
        boolean save = roleService.save(role);
        if (!save) {
            throw new BizException("添加失败");
        }
        return CommonResult.success("添加成功");
    }
}
