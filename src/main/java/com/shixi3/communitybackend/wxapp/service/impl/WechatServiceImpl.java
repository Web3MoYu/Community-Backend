package com.shixi3.communitybackend.wxapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shixi3.communitybackend.Family.entity.WxUser;
import com.shixi3.communitybackend.Family.mapper.WxUserMapper;
import com.shixi3.communitybackend.auth.util.JWTUtils;
import com.shixi3.communitybackend.auth.util.RedisUtils;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.wxapp.DTO.WechatLoginRequestDTO;
import com.shixi3.communitybackend.wxapp.service.WechatService;
import com.shixi3.communitybackend.wxapp.tool.HttpClientUtil;
import com.shixi3.communitybackend.wxapp.vo.WxUserLoginVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WechatServiceImpl implements WechatService {
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String DEFAULT_AVATAR = "https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0";
    private static final String APPID = "wx52ba44d6c0e7b6e1"; //前面获取的appid
    private static final String SECRET = "4bc0617db69cfee1968fd75d579e8235"; //前面获取的小程序密钥，注意要和appid同属于一个项目
    private static final String GRANT_TYPE = "authorization_code";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    WxUserMapper wxUserMapper;

    @Override
    public WxUserLoginVo getToken(WechatLoginRequestDTO loginRequest) throws Exception {
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(loginRequest.getCode());
        WxUserLoginVo vo = new WxUserLoginVo();
        System.out.println("微信返还的openid" + sessionKeyOpenId);
        // 判断是否成功
        if (sessionKeyOpenId.get("errcode") != null) {
            throw new BizException(401, "登陆失败");
        }
        // 获取openId && sessionKey
        String openId = sessionKeyOpenId.getString("openid");
        //String sessionKey = sessionKeyOpenId.getString("session_key");
        // 拼接openid和时间戳
        // 得到token
        String token = JWTUtils.creatToken(openId);
        redisTemplate.opsForValue().set(RedisUtils.WX_TOKEN + openId, token, JWTUtils.EXPIRE_TIME, TimeUnit.DAYS);
        //判断数据库是否存在该用户
        vo.setToken(token);
        LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WxUser::getWxId, openId);
        WxUser wxUser = wxUserMapper.selectOne(wrapper);
        if (wxUser == null) {
            wxUser = new WxUser(null, openId, null, "微信用户", null, null,
                    DEFAULT_AVATAR,null, LocalDateTime.now(), LocalDateTime.now(),
                    null, null, "昵称");
            wxUserMapper.insert(wxUser);
        }
        wxUser = wxUserMapper.selectOne(wrapper);
        vo.setUser(wxUser);
        return vo;
    }

    //调用微信接口服务获取openId && sessionKey
    private JSONObject getSessionKeyOrOpenId(String code) throws Exception {
        Map<String, String> requestUrlParam = new HashMap<>();
        // 小程序appId，自己补充
        requestUrlParam.put("appid", APPID);
        // 小程序secret，自己补充
        requestUrlParam.put("secret", SECRET);
        // 小程序端返回的code
        requestUrlParam.put("js_code", code);
        // 默认参数
        requestUrlParam.put("grant_type", GRANT_TYPE);
        // post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpClientUtil.doPost(REQUEST_URL, requestUrlParam);
        return JSON.parseObject(result);
    }

}