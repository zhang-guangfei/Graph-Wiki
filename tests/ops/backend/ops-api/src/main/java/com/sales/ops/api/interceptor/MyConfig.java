package com.sales.ops.api.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：配置拦截器 ip过滤器
 * @date ：Created in 2022/6/3 15:58
 */
//@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        IPInterceptor loginInterceptor = new IPInterceptor();
        String[] path = {"/**"}; // 如果拦截全部可以设置为 /**
        String[] excludePath = {"/login"}; // 不需要拦截的接口路径
        registry.addInterceptor(loginInterceptor).addPathPatterns(path).excludePathPatterns(excludePath);
    }
}