package com.sales.ops.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author C12961
 * @date 2022/8/2 10:58
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // v2 不同
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sales.ops")) // 设置扫描路径
                .build();
    }

}