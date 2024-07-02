package com.shixi3.communitybackend.common.config;

import com.shixi3.communitybackend.common.handler.CommonExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfigure {

    @Bean
    // 配置公共异常处理器
    public CommonExceptionHandler commonExceptionHandler() {
        return new CommonExceptionHandler();
    }
}
