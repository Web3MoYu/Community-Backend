package com.shixi3.communitybackend.common.util;

import com.shixi3.communitybackend.auth.util.RedisUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.UUID;

@Component
public class ImgUtils {

    @Resource
    RedisTemplate<String,Object> redisTemplate;
    private final String basePath = "c:/img/";

    public String redisUploadImg(String redisSuffix, String redisByte){
        // 获取字节数组和后缀
        String suffix = (String) redisTemplate.opsForValue().get(redisSuffix);
        byte[] bytes = (byte[]) redisTemplate.opsForValue().get(redisByte);
        String avatar = UUID.randomUUID() + suffix;
        // 上传
        upload(bytes, avatar);
        return avatar;
    }


    public void upload(byte[] bytes, String fileName) {
        // 判断当前目录是否存在，如果不存在就创建
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            // 将临时文件转存到指定位置
            FileCopyUtils.copy(bytes, new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void download(String name, HttpServletResponse response) {
        try {
            // 从输入流中读取文件
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(basePath + name));

            response.setContentType("image/jpeg");

            // 向浏览器中输出数据,用于展示图片
            int len = -1;
            byte[] bytes = new byte[1024];
            ServletOutputStream os = response.getOutputStream();
            while ((len = bis.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                os.flush();
            }
            // 关闭资源
            os.close();
            bis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
