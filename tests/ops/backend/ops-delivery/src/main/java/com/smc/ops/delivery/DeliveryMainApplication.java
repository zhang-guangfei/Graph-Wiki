package com.smc.ops.delivery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.redis.redisson.RedissonConfig;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.CreateTokenForOtherServer;

/**
 * @author ：C14717
 * @version: 1.0$ @description：
 * @date ：Created in 2023/6/6 10:59
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = { "com.smc.smccloud.service", "com.sales.ops.feign" })
@MapperScan(basePackages = { "com.smc.ops.delivery.mapper", "com.smc.ops.delivery.inquiry.mapper",
		"com.smc.ops.delivery.po.mapper" })
@ComponentScan(basePackages = { "com.smc.smccloud",
		"com.smc.ops.delivery" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
				RedissonConfig.class, AuthFeignAutoConfiguration.class, RedissonUtil.class,
				CreateTokenForOtherServer.class }))
public class DeliveryMainApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeliveryMainApplication.class, args);
	}
}
