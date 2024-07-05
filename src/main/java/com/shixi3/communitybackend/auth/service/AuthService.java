package com.shixi3.communitybackend.auth.service;


import com.shixi3.communitybackend.auth.vo.ChangePasswordVo;
import com.shixi3.communitybackend.auth.vo.LoginRepVo;
import com.shixi3.communitybackend.auth.vo.LoginReqVo;
import com.shixi3.communitybackend.auth.vo.TokenRepVo;
import com.shixi3.communitybackend.common.model.CommonResult;

public interface AuthService {
    CommonResult<LoginRepVo> login(LoginReqVo query);

    CommonResult<TokenRepVo> token();

    CommonResult<?> logout();

    CommonResult<?> changePassword(ChangePasswordVo body);

    CommonResult<String> changeInfo(String phone);
}
