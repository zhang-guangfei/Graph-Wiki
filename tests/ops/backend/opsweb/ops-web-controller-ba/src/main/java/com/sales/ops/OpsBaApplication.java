package com.sales.ops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.sales.ops.feign"})
@MapperScan(basePackages = {"com.sales.ops.db", "com.sales.ops.serviceimpl.exchange.mapper"})
@EnableTransactionManagement
public class OpsBaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpsBaApplication.class, args);
    }
}
