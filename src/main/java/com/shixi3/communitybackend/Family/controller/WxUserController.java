package com.shixi3.communitybackend.Family.controller;

import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.common.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/WxUser")
@Slf4j
public class WxUserController {
    @Autowired
    private WxUserService wxUserService;

    /**
     * 通过身份证获取微信用户
     * @param idCard
     * @return
     */
    @GetMapping("/getWxUserByIdCard")
    public CommonResult<WxUser> getWxUserByIdCard(String idCard) {
        WxUser wxUser = wxUserService.getWxUserByIdCard(idCard);
        if (wxUser != null) {
            return CommonResult.success(wxUser);
        }else {
            return CommonResult.error(0,"获取用户失败");
        }
    }
}
