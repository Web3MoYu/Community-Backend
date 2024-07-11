package com.shixi3.communitybackend.wxapp.controller;

import com.shixi3.communitybackend.auth.util.RedisUtils;
import com.shixi3.communitybackend.building.service.BuildingService;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.common.util.ImgUtils;
import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;
import com.shixi3.communitybackend.wxapp.service.HouseExamineService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/wx")
public class HouseExamineController {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    HouseExamineService houseExamineService;

    @Resource
    private BuildingService buildingService;

    @Resource
    ImgUtils imgUtils;

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping("/img/upload")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        String userID = ((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        try {
            String byteKey = RedisUtils.HOUSE_IMG_UPLOAD_BYTE + userID;
            String suffixKey = RedisUtils.HOUSE_IMG_UPLOAD_SUFFIX + userID;
            imgUtils.uploadToRedis(file, byteKey, suffixKey);
        } catch (IOException e) {
            throw new BizException("文件上传失败");
        }
        return CommonResult.success("文件上传成功");
    }

    //提交房屋认证信息
    @PostMapping("/addHouseRecord")
    public CommonResult<String> addHouseExamine(@RequestBody TenantExamineRecord record) {
        return houseExamineService.changeInfo(record);
    }

}
