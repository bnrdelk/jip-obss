package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "serviceImpl")
    public IService serviceImpl() {
        return new ServiceImpl();
    }

    @Bean(name = "serviceImpl2")
    public IService serviceImpl2() {
        return new ServiceImpl2();
    }
}