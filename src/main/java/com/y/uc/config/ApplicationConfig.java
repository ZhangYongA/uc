package com.y.uc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y.uc.util.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangyong on 2017/7/21.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
