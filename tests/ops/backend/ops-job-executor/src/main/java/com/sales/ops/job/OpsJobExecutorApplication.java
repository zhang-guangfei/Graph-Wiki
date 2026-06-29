package com.sales.ops.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages= {"com.sales.ops.feign"})
@SpringBootApplication
public class OpsJobExecutorApplication {

	public static void main(String[] args) {
        SpringApplication.run(OpsJobExecutorApplication.class, args);
	}

}