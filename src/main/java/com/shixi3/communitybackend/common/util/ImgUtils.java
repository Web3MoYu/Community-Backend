package com.shixi3.communitybackend.common.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImgUtils {

    private final String basePath = "c:/img/";

    public void upload(MultipartFile file) {
        // 原始文件名字
        String originalFilename = file.getOriginalFilename();

        // 获取文件后缀
        String suffix = null;
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 使用UUID来生成文件名，防止文件名重复
        String fileName = UUID.randomUUID() + suffix;

        // 判断当前目录是否存在，如果不存在就创建
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            // 将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
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
