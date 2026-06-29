package com.smc.smccloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.smc.smccloud.service"})
//@ComponentScan(excludeFilters =
//        {
//                @ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.smc.smccloud.core.security.service.*")
//        })
public class OpsProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpsProductApplication.class, args);
    }

}