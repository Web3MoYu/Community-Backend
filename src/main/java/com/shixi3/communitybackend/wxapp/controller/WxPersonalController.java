package com.shixi3.communitybackend.wxapp.controller;

import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wx/personal")
public class WxPersonalController {

    @Resource
    WxUserService wxUserService;


    @PostMapping("/update")
    public CommonResult<String> updateWxUser(@RequestBody WxUser wxUser) {
        log.info(wxUser.toString());
        wxUserService.updateById(wxUser);
        return CommonResult.success("成功");
    }
}
