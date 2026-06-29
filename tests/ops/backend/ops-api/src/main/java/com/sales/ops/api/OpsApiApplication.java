package com.sales.ops.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author C02483
 * @version 1.0
 * @description: API启动类
 * @date 2021/9/23 10:39
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages= {"com.sales.ops.feign","com.smc.smccloud.service","com.sales.ops.api.feign"})
@ComponentScan(basePackages={"com.sales.ops.common","com.sales.ops.api"})
@EnableScheduling
@MapperScan(basePackages = {"com.sales.ops.db"})
@EnableTransactionManagement
public class OpsApiApplication {
    public static void main(String[] args) {
        //bugid:11144 c14717 20230619 解决附件名称乱码 UTF-8Q
        System.setProperty("mail.mime.splitlongparameters","false");
        SpringApplication.run(OpsApiApplication.class, args);
    }
}
