package com.smc.smccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author lyc
 * @Date 2023/5/30 16:49
 * @Descripton TODO
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class OpsLogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpsLogServiceApplication.class, args);
    }
}
