package com.shixi3.communitybackend.auth.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.shixi3.communitybackend.auth.util.SecurityUtil;
import com.shixi3.communitybackend.auth.util.ServletUtil;
import com.shixi3.communitybackend.common.enums.ErrorCodes;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.common.model.DMSUserDetail;
import com.shixi3.communitybackend.auth.util.JWTUtils;
import com.shixi3.communitybackend.auth.util.RedisUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class TokenFilter extends OncePerRequestFilter {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, ServletException {
        String token = request.getHeader("token");
        if (StringUtils.hasLength(token)) {
            try {
                Long userId = JWTUtils.getUserId(token);
                String cacheToken = redisTemplate
                        .opsForValue().get(RedisUtils.TOKEN_KEY + userId);
                // 验证token是否被修改
                if (cacheToken == null || !cacheToken.equals(token)) {
                    throw new BadCredentialsException("Token error");
                }
                DMSUserDetail userDetails = new DMSUserDetail();
                userDetails.setID(userId);
                String rawStr = redisTemplate
                        .opsForValue().get(RedisUtils.PERMISSIONS_KEY + userId);
                ArrayList<String> authorities = JSON.parseObject(rawStr, ArrayList.class);
                if (authorities == null) {
                    throw new AccessDeniedException("No permission");
                }

                //将验证过后的用户信息放入context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, SecurityUtil.convertStringToGrantedAuthority(authorities));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException | AuthenticationException e) {
                CommonResult<?> commonResult = CommonResult.error(ErrorCodes.UNAUTHORIZED);
                ServletUtil.writeJSON(response, commonResult);
                return;
            } catch (AccessDeniedException e) {
                CommonResult<?> commonResult = CommonResult.error(ErrorCodes.FORBIDDEN);
                ServletUtil.writeJSON(response, commonResult);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
