package com.shixi3.communitybackend.auth.handler;

import com.shixi3.communitybackend.auth.util.JWTUtils;
import com.shixi3.communitybackend.common.enums.ErrorCodes;
import com.shixi3.communitybackend.common.model.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class SecurityExceptionHandler {
    // 权限异常处理
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException e) {
        String token = req.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            log.warn("[authenticationExceptionHandler][ 无法访问 url({})]",
                    req.getRequestURL(), e);
            return CommonResult.error(ErrorCodes.UNAUTHORIZED);
        }
        log.warn("[accessDeniedExceptionHandler][userId({}) 无法访问 url({})]", JWTUtils.getId(token),
                req.getRequestURL(), e);
        return CommonResult.error(ErrorCodes.FORBIDDEN);
    }

    // 授权异常处理
    @ExceptionHandler(AuthenticationException.class)
    public CommonResult<?> authenticationExceptionHandler(HttpServletRequest req, AuthenticationException e) {
        log.warn("[authenticationExceptionHandler][ 无法访问 url({})]",
                req.getRequestURL(), e);
        return CommonResult.error(ErrorCodes.UNAUTHORIZED);
    }
}
