package com.shixi3.communitybackend.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.shixi3.communitybackend.auth.mapper.UserMapper;
import com.shixi3.communitybackend.auth.service.AuthService;
import com.shixi3.communitybackend.auth.util.SecurityUtil;
import com.shixi3.communitybackend.auth.vo.*;
import com.shixi3.communitybackend.common.util.ImgUtils;
import com.shixi3.communitybackend.sys.service.MenuService;
import com.shixi3.communitybackend.common.entity.MenuTree;
import com.shixi3.communitybackend.common.entity.User;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.auth.util.DigestsUtils;
import com.shixi3.communitybackend.auth.util.JWTUtils;
import com.shixi3.communitybackend.auth.util.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    UserMapper userMapper;

    @Resource
    ImgUtils imgUtils;

    @Resource
    MenuService menuService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public CommonResult<LoginRepVo> login(LoginReqVo query) {
        User user = userMapper.getUserByUsername(query.getUsername());
        // 用户名不存在抛出异常信息
        if (user == null) {
            throw new BizException("用户名或密码错误");
        }
        // 匹配密码是否正确
        if (!DigestsUtils.matches(query.getPassword(), user.getSalt(), user.getPassword())) {
            throw new BizException("用户名或密码错误");
        }

        // 拿到权限信息和token存到redis中
        String token = JWTUtils.creatToken(user.getUserId());
        List<MenuTree> menuTree = menuService.getTreeMenu(user.getUserId());
        List<String> authorities = userMapper.getAuthorities(user.getUserId());
        redisTemplate.opsForValue().set(RedisUtils.TOKEN_KEY + user.getUserId(),
                token, JWTUtils.EXPIRE_TIME, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(RedisUtils.PERMISSIONS_KEY + user.getUserId(),
                JSON.toJSONString(authorities), JWTUtils.EXPIRE_TIME, TimeUnit.DAYS);

        LoginRepVo loginRepVo = new LoginRepVo();
        loginRepVo.setMenuTree(menuTree);
        loginRepVo.setToken(token);
        return CommonResult.success(loginRepVo);
    }

    @Override
    public CommonResult<TokenRepVo> token() {
        Long userID = SecurityUtil.getUserID();
        User user = userMapper.selectById(userID);
        TokenRepVo tokenRepVo = new TokenRepVo();
        BeanUtils.copyProperties(user, tokenRepVo);
        // 拿到当前用户的菜单信息
        List<MenuTree> treeMenu = menuService.getTreeMenu(tokenRepVo.getUserId());
        tokenRepVo.setMenuTree(treeMenu);
        return CommonResult.success(tokenRepVo);
    }

    @Override
    public CommonResult<?> logout() {
        Long userID = SecurityUtil.getUserID();
        // 删除权限和身份信息在redis服务器中
        redisTemplate.delete(RedisUtils.TOKEN_KEY + userID);
        redisTemplate.delete(RedisUtils.PERMISSIONS_KEY + userID);
        return CommonResult.success("");
    }

    @Override
    public CommonResult<String> changeInfo(String phone) {
        Long userID = SecurityUtil.getUserID();

        String redisSuffix = RedisUtils.PERSONAL_IMG_UPLOAD_SUFFIX + userID;
        String redisByte = RedisUtils.PERSONAL_IMG_UPLOAD_BYTE + userID;
        // 上传
        String avatar = imgUtils.uploadToSystem(redisSuffix, redisByte);
        // 修改数据库
        userMapper.updatePhoneAndAvatar(phone, userID, avatar);

        // 删除redis图片数据
        redisTemplate.delete(redisSuffix);
        redisTemplate.delete(redisByte);
        return CommonResult.success("更新成功");
    }

    @Override
    public CommonResult<?> changePassword(ChangePasswordVo body) {
        Long userID = SecurityUtil.getUserID();
        User user = userMapper.selectById(userID);
        if (!DigestsUtils.matches(body.getOldPassword(), user.getSalt(), user.getPassword())) {
            throw new BizException("旧密码错误");
        }
        String newPassword = body.getNewPassword();
        Map<String, String> encrypt = DigestsUtils.encrypt(newPassword);
        int count = userMapper.updatePassword(userID,
                encrypt.get(DigestsUtils.PASSWORD), encrypt.get(DigestsUtils.SALT));
        if (count != 1) {
            throw new BizException("修改失败");
        }
        redisTemplate.delete(RedisUtils.TOKEN_KEY + userID);
        redisTemplate.delete(RedisUtils.PERMISSIONS_KEY + userID);
        return CommonResult.success("修改成功");
    }


}
