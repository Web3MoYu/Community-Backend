package com.shixi3.communitybackend.Family.controller;

import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/WxUser")
@Slf4j
public class WxUserController {
    @Resource
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

    /**
     * 根据微信用户表主键获取微信用户
     * @param id
     * @return
     */
    @GetMapping("/getWxUserById/{id}")
    public CommonResult<WxUser> getWxUserById(@PathVariable Long id) {
        WxUser wxUser = wxUserService.getWxUserById(id);
        if (wxUser != null) {
            return CommonResult.success(wxUser);
        }else {
            throw new BizException("获取用户失败!");
        }
    }

    /**
     * 统计数量
     *
     * @return
     */
    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<Long> count() {
        long count = wxUserService.count();
        return CommonResult.success(count);
    }



}
