package com.y.uc.config;

import com.y.uc.filter.PrincipalFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Created by zhangyong on 2017/7/21.
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean principalFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(principalFilter());
        registration.addUrlPatterns("/*");
        registration.setName("principalFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public Filter principalFilter() {
        return new PrincipalFilter();
    }

}
