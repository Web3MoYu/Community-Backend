package com.shixi3.communitybackend.auth.controller;

import com.shixi3.communitybackend.auth.service.AuthService;
import com.shixi3.communitybackend.auth.vo.ChangePasswordVo;
import com.shixi3.communitybackend.auth.vo.LoginRepVo;
import com.shixi3.communitybackend.auth.vo.LoginReqVo;
import com.shixi3.communitybackend.auth.vo.TokenRepVo;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Resource
    public AuthService authService;

    @PermitAll
    @PostMapping("/login")
    // 登录接口
    public CommonResult<LoginRepVo> login(@RequestBody LoginReqVo loginReqVo) {
        return authService.login(loginReqVo);
    }

    // 使用token进行验证及登录
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/token")
    public CommonResult<TokenRepVo> token() {
        return authService.token();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    public CommonResult<?> logout() {
        return authService.logout();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/info/{phone}")
    public CommonResult<String> changeInfo(@PathVariable String phone) {
        return authService.changeInfo(phone);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/password")
    public CommonResult<?> changePassword(@RequestBody ChangePasswordVo body) {
        return authService.changePassword(body);
    }
}
