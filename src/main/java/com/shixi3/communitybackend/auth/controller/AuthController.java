package com.shixi3.communitybackend.auth.controller;

import com.shixi3.communitybackend.auth.service.AuthService;
import com.shixi3.communitybackend.auth.vo.LoginRepVo;
import com.shixi3.communitybackend.auth.vo.LoginReqVo;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
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
}
