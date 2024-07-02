package com.shixi3.communitybackend.auth.util;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletUtil {
    // 写出数据给前端
    public static void writeJSON(HttpServletResponse response, Object object) throws IOException {
        String content = JSON.toJSONString(object);
        response.getWriter().write(content);
    }
}