package com.smc.smccloud.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;


/**
 * 资源服务配置
 */
@Slf4j
public abstract class BaseResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Resource
    private FilterIgnoresUrlConfig filterIgnoresUrlConfig;

    @Resource
    private AuthAccessDeniedHandler authAccessDeniedHandler;

    @Resource
    private AuthExceptionEntryPoint authExceptionEntryPoint;

    @Resource
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Resource
    private TokenStore jwtTokenStore;

    /**
     * The Redis key prefix for caches
     */
//    public static final String DEFAULT_CACHE_KEY_PREFIX = Constants.REDIS_CACHE_SESSION_PREFIX;
//    private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;
//
//    public String getKeyPrefix() {
//        return keyPrefix;
//    }
//
//    public void setKeyPrefix(String keyPrefix) {
//        this.keyPrefix = keyPrefix;
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("*******--验证路径权限*******--");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();

        registry.antMatchers(
                "/resources/**",
                "/webjars/**",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/**/api-docs",
                "/v2/**",
                "/v3/**",
                "/oauth/hasPermission",
                "/product/**",
                "/swagger-ui.html",
                "/doc.html")
                .permitAll();


        filterIgnoresUrlConfig.getUrls()
                .forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().access("@rbacAuthorityService.hasPermission(request,authentication)");
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.expressionHandler(expressionHandler)
                .tokenStore(jwtTokenStore)  // TokenStore类的实例，指定令牌如何访问，与tokenServices配置可选
                .authenticationEntryPoint(authExceptionEntryPoint)
                .accessDeniedHandler(authAccessDeniedHandler);
    }




}
