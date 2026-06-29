package com.smc.smccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author edp04
 * @title: OpsReportApplication
 * @date 2022/05/10 14:40
 */
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages= {"com.sales.ops.feign","com.smc.smccloud.service"})
@EnableTransactionManagement
public class OpsReportApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(OpsReportApplication.class, args);
    }

}