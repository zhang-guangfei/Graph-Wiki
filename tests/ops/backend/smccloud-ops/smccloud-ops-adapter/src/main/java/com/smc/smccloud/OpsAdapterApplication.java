package com.smc.smccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Author: B90034
 * Date: 2022-02-14 15:44
 * Description: OPS与门户交互服务
 */
@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.smc.smccloud.service", "com.sales.ops.feign"})
public class OpsAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpsAdapterApplication.class, args);
    }
}
