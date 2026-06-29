//package com.smc.smccloud.core.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//
//
//@Configuration
//@EnableResourceServer
//public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Value("${smccloud.oauth2.resource.token-info-uri}")
//    private String tokenInfoUri;
//    @Value("${smccloud.oauth2.client.clientId}")
//    private String clientId;
//    @Value("${smccloud.oauth2.client.clientSecret}")
//    private String clientSecret;
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("app"); // 所要访问的资源列表（id）
//        // 验证令牌服务
//        resources.tokenServices(tokenService());
//    }
//
//    // 资源服务令牌解析服务
//    @Bean
//    public ResourceServerTokenServices tokenService() {
//        // 使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret
//        RemoteTokenServices service=new RemoteTokenServices();
//        service.setCheckTokenEndpointUrl(tokenInfoUri); // 在授权服务已经开放
//        service.setClientId(clientId);
//        service.setClientSecret(clientSecret);
//        return service;
//    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();  // .authorizeRequests().anyRequest().authenticated();
//    }
//}
