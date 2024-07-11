package com.shixi3.communitybackend.common.util;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ImgUtils {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    private final String basePath = "c:/img/";

    public String uploadToSystem(String redisSuffix, String redisByte) {
        // 获取字节数组和后缀
        String suffix = (String) redisTemplate.opsForValue().get(redisSuffix);
        byte[] bytes = (byte[]) redisTemplate.opsForValue().get(redisByte);
        String avatar = UUID.randomUUID() + suffix;
        // 上传
        upload(bytes, avatar);
        return avatar;
    }

    public void uploadToRedis(MultipartFile file, String bytesKey, String suffixKey) throws IOException {

        // 首先将bytes和后缀放到redis中
        byte[] bytes = file.getBytes();
        String originalFilename = file.getOriginalFilename();
        String suffix = ".png";
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 图片过期时间
        int expireTime = 5;
        redisTemplate.opsForValue().set(bytesKey, bytes,
                expireTime, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(suffixKey, suffix,
                expireTime, TimeUnit.MINUTES);
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
