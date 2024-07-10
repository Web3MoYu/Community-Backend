package com.shixi3.communitybackend.wxapp.controller;

import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/wx/personal")
public class WxPersonalController {

    @Resource
    WxUserService wxUserService;


    @PostMapping("/update")
    public CommonResult<String> updateWxUser(@RequestBody WxUser wxUser) {
        wxUser.setUpdateTime(LocalDateTime.now());
        wxUserService.updateById(wxUser);
        return CommonResult.success("成功");
    }
}
