package com.sales.ops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.smc.smccloud.service", "com.sales.ops.feign","com.sales.ops.service.feign"})
@MapperScan(basePackages = {"com.sales.ops.db", "com.sales.ops.serviceimpl.event.v3.status.mapper","com.sales.ops.event.repository.mapper"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class OpsWmApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpsWmApplication.class, args);
    }
}
