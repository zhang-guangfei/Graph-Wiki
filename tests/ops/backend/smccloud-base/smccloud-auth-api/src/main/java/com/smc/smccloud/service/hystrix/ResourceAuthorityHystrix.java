//package com.smc.smccloud.service.hystrix;
//
//import com.smc.smccloud.service.ResourceAuthorityApi;
//import org.springframework.cloud.openfeign.FallbackFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//@Slf4j
//public class ResourceAuthorityHystrix implements FallbackFactory<ResourceAuthorityApi>{
//
//    @Override
//    public ResourceAuthorityApi create(Throwable throwable) {
//        return new ResourceAuthorityApi() {
////            @Override
////            public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
////                log.error("服务降级");
////                return false;
////            }
//        };
//    }
//}
