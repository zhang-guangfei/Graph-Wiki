package com.sales.ops.feign.config;

import feign.Logger;

import org.springframework.context.annotation.Bean;


/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/10/13 14:43
 */
public class FeignConfig{
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // 输出请求头、body
    }
}
