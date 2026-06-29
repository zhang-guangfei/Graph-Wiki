package com.smc.smccloud.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;

@Slf4j
@Configuration
@Order(value = 5)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("----into web拦截----");
        http
                .cors().and()
                .headers().disable()
                .csrf().disable() // 关闭csrf保护
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // 满足条件的放行
                .anyRequest().authenticated()  // 所有的请求都要经过认证
        ;
    }
}
