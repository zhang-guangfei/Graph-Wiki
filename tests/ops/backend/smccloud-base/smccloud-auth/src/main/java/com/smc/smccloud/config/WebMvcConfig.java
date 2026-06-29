//package com.smc.smccloud.config;
//
//import com.smc.smccloud.config.Interceptor.AuthorizationInterceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@Slf4j
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//
//    @Autowired
//    private AuthorizationInterceptor authorizationInterceptor;
//
////    /*
////    跨域设置
////     */
////    private CorsConfiguration buildConfig() {
////        CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
////        corsConfiguration.addAllowedHeader("*"); // 允许任何头
////        corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
////        return corsConfiguration;
////    }
////
////    @Bean
////    public CorsFilter corsFilter() {
////        log.info("*********----跨域设置----------------");
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置
////        return new CorsFilter(source);
////    }
//
//
//    /**
//     * 拦截器配置
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
//    }
//
//
//}
