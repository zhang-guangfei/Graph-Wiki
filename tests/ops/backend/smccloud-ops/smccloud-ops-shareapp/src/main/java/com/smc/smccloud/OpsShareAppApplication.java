package com.smc.smccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Author: B90034
 * Date: 2021-09-27 11:35
 * Description:
 */
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.smc.smccloud.service", "com.sales.ops.feign"})
@EnableTransactionManagement
@EnableConfigurationProperties
public class OpsShareAppApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(OpsShareAppApplication.class, args);
    }

}
