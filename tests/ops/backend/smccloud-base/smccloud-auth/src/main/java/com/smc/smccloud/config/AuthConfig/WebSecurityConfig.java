package com.smc.smccloud.config.AuthConfig;

import com.smc.smccloud.service.SsoTokenVerifyService;
import com.smc.smccloud.sso.SsoAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * Web安全配置
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter
{

    /*@Resource
     private LdapService ldapService;*/

    @Resource
    private SsoTokenVerifyService ssoTokenVerifyService;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 安全拦截机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
             //.apply(ssoAuthenticationSecurityConfig).and()
             .requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/**").and()
             .cors().and()
             .headers().disable()
             .csrf().disable() // 关闭csrf保护
             .authorizeRequests()
             // .antMatchers(SsoAuthenticationFilter.SSO_LOGIN).permitAll()
             .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
             .anyRequest().authenticated().and()  // 所有的请求都要经过登录认证
             .formLogin()
//             .loginPage("/login.html")
//             .loginProcessingUrl("/login")
             .and()
             .logout().permitAll();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/console/**");
        web.ignoring().antMatchers("/scripts/**");
        web.ignoring().antMatchers("/login.html");
//        web.ignoring()
//                .antMatchers(
//                        "/webjars/**",
//                        "/resources/**",
//                        "/swagger-ui/index.html",
//                        "/swagger-resources/**",
//                        "/**/api-docs",
//                        "/v2/**");
        web.ignoring().antMatchers("/userlogin","/userlogout","/userjwt","/v2/api-docs", "/swagger-resources/configuration/ui",
               "/swagger-resources","/swagger-resources/configuration/security",
                "/doc.html","/swagger-resources/configuration","/v3/api-docs","/v2/api-docs","/webjars/**",
                "/swagger-ui.html","/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        WebDaoAuthenticationProvider daoAuthenticationProvider = new WebDaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(provider());
    }

    /**
     * 设置密码的加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //使用这种方式 需要配置一个加解密类
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    AuthenticationManager 认证管理器  在AuthorizationServerConfig中引用并使用
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SsoAuthenticationProvider provider() {
        SsoAuthenticationProvider provider = new SsoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setSsoTokenVerifyService(ssoTokenVerifyService);
        return provider;
    }


}
