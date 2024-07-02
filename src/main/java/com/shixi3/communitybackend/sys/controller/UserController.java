package com.shixi3.communitybackend.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.auth.service.UserService;
import com.shixi3.communitybackend.common.entity.User;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 搜索
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public CommonResult<Page<User>> search(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize, @RequestParam(required = false) String name) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUserId, User::getName, User::getPhone, User::getUsername, User::getSex)
                .like(name != null, User::getName, name);
        Page<User> result = userService.page(new Page<>(page, pageSize), wrapper);
        return CommonResult.success(result);
    }
}
