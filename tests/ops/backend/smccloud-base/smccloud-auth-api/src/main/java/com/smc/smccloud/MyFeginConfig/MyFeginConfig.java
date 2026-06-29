package com.smc.smccloud.MyFeginConfig;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFeginConfig
{
    //自定义重试次数
    @Bean
    public Retryer feignRetryer() {
        Retryer retryer = new Retryer.Default(100, 1000, 4);
        return retryer;
    }

}
