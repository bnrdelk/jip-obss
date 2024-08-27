package com.example.restapi.configuration;

import com.example.restapi.filter.RequestLogFilter;
import com.example.restapi.filter.RequestLogOncePerRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestLogFilter> requestLogFilter() {
        FilterRegistrationBean<RequestLogFilter> bean = new FilterRegistrationBean<>(new RequestLogFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/users/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLogOncePerRequestFilter> oncePerRequestLogFilter() {
        FilterRegistrationBean<RequestLogOncePerRequestFilter> bean = new FilterRegistrationBean<>(new RequestLogOncePerRequestFilter());
        bean.setOrder(100);
        bean.addUrlPatterns("/*");
        return bean;
    }
}

