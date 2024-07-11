package com.shixi3.communitybackend.wxapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.auth.util.RedisUtils;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.wxapp.DTO.WechatLoginRequestDTO;
import com.shixi3.communitybackend.wxapp.service.WechatService;
import com.shixi3.communitybackend.wxapp.vo.WxUserLoginVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/wx")
public class LoginController {

    @Resource
    WechatService wechatService;

    @Resource
    WxUserMapper wxUserMapper;

    @Resource
    RedisTemplate<String, Object> redisTemplate;


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

    @GetMapping("/logout")
    public CommonResult<String> logout() {
        String wxId = ((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // 删除redis中的token
        Boolean delete = redisTemplate.delete(RedisUtils.WX_TOKEN + wxId);
        if (!Boolean.TRUE.equals(delete)) {
            throw new BizException("退出登录失败");
        }
        return CommonResult.success("退出成功");
    }


}
