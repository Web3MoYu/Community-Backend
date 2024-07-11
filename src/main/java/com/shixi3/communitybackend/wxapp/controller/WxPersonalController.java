package com.shixi3.communitybackend.wxapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/list")
    public CommonResult<List<WxUser>> list(){
        LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(WxUser::getUserType, 3);
        List<WxUser> list = wxUserService.list(wrapper);
        return CommonResult.success(list);
    }
}
