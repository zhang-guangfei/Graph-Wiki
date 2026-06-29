package com.smc.smccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.smc.smccloud.service"})
@EnableCaching
public class SmccloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmccloudAuthApplication.class, args);
    }

}
