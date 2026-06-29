package com.smc.smccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.smc.smccloud.service", "com.sales.ops.feign"})
@EnableCaching
@EnableTransactionManagement
@EnableAsync
public class OpsCPFRApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpsCPFRApplication.class, args);
    }

}
