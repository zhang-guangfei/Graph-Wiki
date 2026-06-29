package com.smc.smccloud.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smc.smccloud.core.interceptor.TokenInterceptor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.jiderhamn.classloader.leak.prevention.ClassLoaderLeakPreventorListener;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebAppConfig implements WebMvcConfigurer {


    @Resource
    public TokenInterceptor tokenInterceptor;

    // @Resource
    // private RestTokenInterceptor restTokenInterceptor;

    // @Bean
    // public RestTemplate restTemplate() {
    // RestTemplate restTemplate = new RestTemplate();
    // restTemplate.getInterceptors().add(restTokenInterceptor);
    // return restTemplate;
    // }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokenInterceptor).excludePathPatterns("/auth");

    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(getObjectMapper());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //忽略多余字段
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        // 解决中文乱码
        converters.add(responseBodyConverter());
        converters.add(messageConverter());
    }

    /**
     * 防止RequestContextHolder为空
     *
     * @return
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    /**
     * 避免LaunchedURLClassLoader导致的内存泄漏 bug-9272
     */
    @Bean
    public ServletListenerRegistrationBean registerClassLoaderLeakPrevent() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new ClassLoaderLeakPreventorListener());
        servletListenerRegistrationBean.setOrder(1);
        return servletListenerRegistrationBean;
    }

}