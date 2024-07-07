package com.shixi3.communitybackend.wxapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.wxapp.DTO.WechatLoginRequestDTO;
import com.shixi3.communitybackend.wxapp.service.WechatService;
import com.shixi3.communitybackend.wxapp.vo.WxUserLoginVo;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
public class LoginController {

    @Resource
    WechatService wechatService;

    @Resource
    WxUserMapper wxUserMapper;

    @PostMapping("/wxlogin")
    public CommonResult<WxUserLoginVo> login(@RequestBody WechatLoginRequestDTO loginRequest) {
        System.out.println("收到微信code：" + loginRequest.getCode());
        try {
            WxUserLoginVo vo = wechatService.getToken(loginRequest);
            return CommonResult.success(vo);
        } catch (Exception e) {
            throw new BizException(401, "登陆失败");
        }
    }

    @GetMapping("/checkwxlogin")
    public CommonResult<WxUser> checkLogin() {
        String wxId = ((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WxUser::getWxId, wxId);
        WxUser wxUser = wxUserMapper.selectOne(wrapper);
        return CommonResult.success(wxUser);
    }
}
