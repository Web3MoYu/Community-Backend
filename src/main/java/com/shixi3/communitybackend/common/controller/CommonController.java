package com.shixi3.communitybackend.common.controller;

import com.shixi3.communitybackend.auth.util.RedisUtils;
import com.shixi3.communitybackend.auth.util.SecurityUtil;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.common.util.ImgUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    ImgUtils imgUtils;

    @GetMapping("/img/download/{name}")
    public void download(@PathVariable String name, HttpServletResponse response) {
        imgUtils.download(name, response);
    }

    @PostMapping("/img/upload")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        Long userID = SecurityUtil.getUserID();

        try {
            // 首先将bytes和后缀放到redis中
            byte[] bytes = file.getBytes();
            String originalFilename = file.getOriginalFilename();
            String suffix = ".png";
            if (originalFilename != null) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            // 图片过期时间
            int expireTime = 5;
            redisTemplate.opsForValue().set(RedisUtils.PERSONAL_IMG_UPLOAD_BYTE + userID, bytes,
                    expireTime, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(RedisUtils.PERSONAL_IMG_UPLOAD_SUFFIX + userID, suffix,
                    expireTime, TimeUnit.MINUTES);
        } catch (IOException e) {
            throw new BizException("文件上传失败");
        }
        return CommonResult.success("文件上传成功");
    }

}
