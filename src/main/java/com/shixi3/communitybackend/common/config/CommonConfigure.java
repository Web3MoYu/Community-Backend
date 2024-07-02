package com.shixi3.communitybackend.common.config;

import com.shixi3.communitybackend.common.handler.CommonExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CommonConfigure {

    @Bean
    public CommonExceptionHandler commonExceptionHandler() {
        return new CommonExceptionHandler();
    }
}
