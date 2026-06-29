package com.sales.ops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages= {"com.smc.smccloud.service","com.sales.ops.feign","com.sales.ops.serviceimpl.mdm"})
@MapperScan(basePackages = {"com.sales.ops.db", "com.sales.ops.event.repository.mapper"})
@EnableTransactionManagement
// @ImportAutoConfiguration({ClientAuthFeginConfiguration.class})
public class OpsApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpsApplication.class, args);
    }
}
