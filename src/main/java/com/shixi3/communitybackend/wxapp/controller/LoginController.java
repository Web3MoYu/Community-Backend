package com.shixi3.communitybackend.wxapp.controller;

import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.wxapp.DTO.WechatLoginRequestDTO;
import com.shixi3.communitybackend.wxapp.service.WechatService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
public class LoginController {

    @Resource
    WechatService wechatService;

    @PostMapping("/wxlogin")
    public CommonResult<String> login(@RequestBody WechatLoginRequestDTO loginRequest) {
        System.out.println("收到微信code：" + loginRequest.getCode());
        try {
            String token = wechatService.getToken(loginRequest);
            return CommonResult.success(token);
        } catch (Exception e) {
            throw new BizException(401, "登陆失败");
        }
    }

    @GetMapping("/checkwxlogin")
    public CommonResult<String> checkLogin(@NotNull @RequestParam String token) {
        System.out.println("收到待校验token：" + token);
        try {
            wechatService.checkToken(token);
        } catch (Exception e) {
            throw new BizException(401, "校验失败");
        }
        return CommonResult.success("校验成功");
    }
}
