package com.shixi3.communitybackend.wxapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.Family.service.WxUserService;
import com.shixi3.communitybackend.auth.util.RedisUtils;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.common.util.ImgUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/wx/personal")
public class WxPersonalController {

    @Resource
    WxUserService wxUserService;

    @Resource
    WxUserMapper wxUserMapper;

    @Resource
    ImgUtils imgUtils;


    @PostMapping("/update")
    public CommonResult<String> updateWxUser(@RequestBody WxUser wxUser) {
        wxUser.setUpdateTime(LocalDateTime.now());
        wxUserService.updateById(wxUser);
        return CommonResult.success("成功");
    }

    @GetMapping("/list")
    public CommonResult<List<WxUser>> list() {
        LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(WxUser::getUserType, 3);
        List<WxUser> list = wxUserService.list(wrapper);
        return CommonResult.success(list);
    }

    @PostMapping("/img/upload")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        String userID = ((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        try {
            String byteKey = RedisUtils.WX_PERSONAL_FACE_IMAGE_BYTE + userID;
            String suffixKey = RedisUtils.WX_PERSONAL_FACE_IMAGE_SUFFIX + userID;
            imgUtils.uploadToRedis(file, byteKey, suffixKey);
        } catch (IOException e) {
            throw new BizException("文件上传失败");
        }
        return CommonResult.success("文件上传成功");
    }

    @GetMapping("/img/upload/ok")
    public CommonResult<String> upload() {
        String userID = ((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String byteKey = RedisUtils.WX_PERSONAL_FACE_IMAGE_BYTE + userID;
        String suffixKey = RedisUtils.WX_PERSONAL_FACE_IMAGE_SUFFIX + userID;
        String name = imgUtils.uploadToSystem(suffixKey, byteKey);
        wxUserMapper.updateFaceImag(name, userID);
        return CommonResult.success(name);
    }
}
