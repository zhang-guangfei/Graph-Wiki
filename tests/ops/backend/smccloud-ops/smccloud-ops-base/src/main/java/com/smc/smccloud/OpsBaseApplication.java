package com.smc.smccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
// @ComponentScan("com.smc.smccloud.config.security")
@SpringBootApplication
public class  OpsBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpsBaseApplication.class, args);
	}

}
