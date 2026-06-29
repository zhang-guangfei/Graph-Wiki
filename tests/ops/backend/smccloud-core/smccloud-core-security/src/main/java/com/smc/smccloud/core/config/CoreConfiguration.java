//package com.smc.smccloud.core.config;
//
//import com.smc.smccloud.core.interceptor.TokenInterceptor;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Configuration
//public class CoreConfiguration {
//
//    @Bean
//    @ConditionalOnMissingBean(HandlerInterceptor.class)
//    @ConditionalOnProperty(prefix = "smccloud.token.interceptor", name = "enabled", havingValue = "true")
//    public TokenInterceptor tokenInterceptor() {
//        return new TokenInterceptor();
//    }
//}