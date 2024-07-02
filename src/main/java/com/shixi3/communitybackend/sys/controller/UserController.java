package com.shixi3.communitybackend.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.auth.service.UserService;
import com.shixi3.communitybackend.auth.util.DigestsUtils;
import com.shixi3.communitybackend.common.entity.User;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.sys.entity.UserRole;
import com.shixi3.communitybackend.sys.mapper.RoleMapper;
import com.shixi3.communitybackend.sys.mapper.UserRoleMapper;
import com.shixi3.communitybackend.sys.vo.UserVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    RoleMapper roleMapper;

    @Resource
    UserRoleMapper userRoleMapper;

    /**
     * 搜索
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public CommonResult<Page<UserVo>> search(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize, @RequestParam(required = false) String name) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUserId, User::getName, User::getPhone, User::getUsername, User::getSex, User::getIdCard)
                .like(name != null, User::getName, name);
        Page<User> result = userService.page(new Page<>(page, pageSize), wrapper);
        List<UserVo> collect = result.getRecords().stream().map((item) -> {
            Long userId = item.getUserId();
            String roleName = roleMapper.getRoleByUserID(userId).getRoleName();
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(item, userVo);
            userVo.setRoleName(roleName);
            return userVo;
        }).toList();
        Page<UserVo> userVoPage = new Page<>();
        BeanUtils.copyProperties(result, userVoPage, "records");
        userVoPage.setRecords(collect);
        return CommonResult.success(userVoPage);
    }

    /**
     * 根据id获取用户
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public CommonResult<User> getUserById(@PathVariable Long userId) {
        return CommonResult.success(userService.getById(userId));
    }

    /**
     * 编辑用户
     */
    @PutMapping("/edit/{userId}")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @Transactional
    public CommonResult<String> update(@RequestBody UserVo user, @PathVariable Long userId) {
        user.setUserId(userId);
        user.setUpdateTime(new Date());
        // 是否重置密码
        if (user.isResetPassword()) {
            Map<String, String> map = DigestsUtils.getPassword();
            String salt = map.get(DigestsUtils.SALT);
            String pwd = map.get(DigestsUtils.PASSWORD);
            user.setSalt(salt);
            user.setPassword(pwd);
        }
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        // 修改角色信息
        if (user.getRoleId() != null) {
            userRoleMapper.updateRole(userId, user.getRoleId());
        }
        // 修改其他信息
        wrapper.set(User::getUsername, user.getUsername())
                .set(User::getPhone, user.getPhone())
                .set(User::getName, user.getName())
                .set(User::getSex, user.getSex())
                .set(User::getUpdateTime, user.getUpdateTime())
                .eq(User::getUserId, user.getUserId());
        userService.update(wrapper);
        return CommonResult.success("修改成功");
    }

    /*
   根据username获取用户
    */
    @GetMapping("/{username}/{isEdit}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public CommonResult<Boolean> checkUsername(@PathVariable String username, @PathVariable boolean isEdit) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(username != null, User::getUsername, username);
        User result = userService.getOne(wrapper);
        return CommonResult.success(Objects.isNull(result));
    }

    /**
     * 添加用户
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @Transactional
    public CommonResult<String> add(@RequestBody UserVo user) {
        System.out.println(user);
        // 修改时间
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        // 创建密码
        Map<String, String> map = DigestsUtils.getPassword();
        String salt = map.get(DigestsUtils.SALT);
        String pwd = map.get(DigestsUtils.PASSWORD);
        user.setSalt(salt);
        user.setPassword(pwd);
        userService.save(user);

        // 添加角色信息
        UserRole userRole = new UserRole();
        userRole.setRoleId(user.getRoleId());
        userRole.setUserId(user.getUserId());
        userRoleMapper.insert(userRole);
        return CommonResult.success("添加成功");
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @Transactional
    public CommonResult<String> delete(@PathVariable Long userId) {
        // 首先删除角色信息
        userRoleMapper.deleteUserRoleById(userId);
        //删除角色
        userService.removeById(userId);
        return CommonResult.success("删除成功");
    }
}
