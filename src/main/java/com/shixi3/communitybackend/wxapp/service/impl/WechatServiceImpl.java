package com.shixi3.communitybackend.wxapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.shixi3.communitybackend.auth.util.JWTUtils;
import com.shixi3.communitybackend.auth.util.RedisUtils;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.wxapp.DTO.WechatLoginRequestDTO;
import com.shixi3.communitybackend.wxapp.service.WechatService;
import com.shixi3.communitybackend.wxapp.tool.HttpClientUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WechatServiceImpl implements WechatService {
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String APPID = "wx52ba44d6c0e7b6e1"; //前面获取的appid
    private static final String SECRET = "4bc0617db69cfee1968fd75d579e8235"; //前面获取的小程序密钥，注意要和appid同属于一个项目
    private static final String GRANT_TYPE = "authorization_code";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getToken(WechatLoginRequestDTO loginRequest) throws Exception {
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(loginRequest.getCode());
        System.out.println("微信返还的openid" + sessionKeyOpenId);
        // 获取openId && sessionKey
        String openId = sessionKeyOpenId.getString("openid");
        //String sessionKey = sessionKeyOpenId.getString("session_key");
        // 拼接openid和时间戳
        // 得到token
        String token = JWTUtils.creatToken(openId);
        redisTemplate.opsForValue().set(RedisUtils.WX_TOKEN + openId, token, JWTUtils.EXPIRE_TIME, TimeUnit.DAYS);

        return token;
    }

    @Override
    public void checkToken(String token) {
        String redisToken = (String) redisTemplate.opsForValue().get(RedisUtils.WX_TOKEN);

        if (redisToken == null || !redisToken.equals(token)) {
            throw new BizException("用户令牌有误");
        } else {
            try {
                JWTUtils.verify(token);
            } catch (Exception e) {
                throw new BizException("用户令牌有误");
            }
        }
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