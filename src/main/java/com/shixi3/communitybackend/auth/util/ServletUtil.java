package com.shixi3.communitybackend.auth.util;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServletUtil {
    // 写出数据给前端
    public static void writeJSON(HttpServletResponse response, Object object) throws IOException {
        String content = JSON.toJSONString(object);
        response.setContentType("application/json");
        response.getWriter().write(content);
    }
}