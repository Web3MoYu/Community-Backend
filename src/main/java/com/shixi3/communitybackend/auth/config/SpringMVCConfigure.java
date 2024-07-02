package com.shixi3.communitybackend.auth.config;

import com.shixi3.communitybackend.common.handler.SpringMVCExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.DispatcherServlet;

@AutoConfiguration
@ConditionalOnClass(DispatcherServlet.class)
public class SpringMVCConfigure {
    @Bean
    @Order(0)
    public SpringMVCExceptionHandler springMVCExceptionHandler() {
        return new SpringMVCExceptionHandler();
    }
}
