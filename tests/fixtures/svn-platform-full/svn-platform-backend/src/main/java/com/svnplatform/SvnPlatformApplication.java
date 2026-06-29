package com.svnplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SvnPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SvnPlatformApplication.class, args);
    }
}
