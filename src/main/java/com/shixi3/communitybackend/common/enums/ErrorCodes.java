package com.shixi3.communitybackend.common.enums;


import com.shixi3.communitybackend.common.model.ErrorCode;

public interface ErrorCodes {
    ErrorCode SUCCESS = new ErrorCode(0, "success");

    // ========== 客户端错误段 ==========
    ErrorCode BAD_REQUEST = new ErrorCode(400, "request parameter error");
    ErrorCode UNAUTHORIZED = new ErrorCode(401, "not login");
    ErrorCode FORBIDDEN = new ErrorCode(403, "no authorization");
    ErrorCode NOT_FOUND = new ErrorCode(404, "not found");
    ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "request method not allowed");
    ErrorCode LOCKED = new ErrorCode(423, "request failed"); // 并发请求，不允许
    ErrorCode TOO_MANY_REQUESTS = new ErrorCode(429, "too many requests");

    // ========== 服务端错误段 ==========

    ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "system error");
    ErrorCode NOT_IMPLEMENTED = new ErrorCode(501, "not implemented");

    // ========== 自定义错误段 ==========
    ErrorCode REPEATED_REQUESTS = new ErrorCode(900, "repeated requests"); // 重复请求
    ErrorCode DEMO_DENY = new ErrorCode(901, "demo deny");
    ErrorCode UNKNOWN = new ErrorCode(999, "unknown error");
}
