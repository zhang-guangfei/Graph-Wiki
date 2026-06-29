package com.smc.smccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.smc.smccloud.service"})
public class OpsJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpsJobApplication.class,args);
    }
}
