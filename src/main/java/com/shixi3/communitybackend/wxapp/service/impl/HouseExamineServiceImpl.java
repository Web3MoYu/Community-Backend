package com.shixi3.communitybackend.wxapp.service.impl;

import com.shixi3.communitybackend.auth.util.RedisUtils;
import com.shixi3.communitybackend.auth.util.SecurityUtil;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.common.util.ImgUtils;
import com.shixi3.communitybackend.examine.entity.TenantExamineRecord;
import com.shixi3.communitybackend.examine.mapper.HouseVetMapper;
import com.shixi3.communitybackend.examine.service.HouseVetService;
import com.shixi3.communitybackend.wxapp.service.HouseExamineService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HouseExamineServiceImpl implements HouseExamineService {

    @Resource
    ImgUtils imgUtils;

    @Resource
    HouseVetService houseVetService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public CommonResult<String> changeInfo(TenantExamineRecord record) {
        String userID = ((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        String redisSuffix = RedisUtils.HOUSE_IMG_UPLOAD_SUFFIX + userID;
        String redisByte = RedisUtils.HOUSE_IMG_UPLOAD_BYTE + userID;
        // 上传
        String avatar = imgUtils.redisUploadImg(redisSuffix, redisByte);
        record.setCertImg(avatar);
        // 将数据添加进数据库
        houseVetService.save(record);

        // 删除redis图片数据
        redisTemplate.delete(redisSuffix);
        redisTemplate.delete(redisByte);
        return CommonResult.success("更新成功");
    }
}
