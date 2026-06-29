//package com.smc.smccloud.service;
//
//import com.smc.smccloud.service.hystrix.ResourceAuthorityHystrix;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//@FeignClient(name = "auth-service",contextId = "resource_auth",
//            fallback = ResourceAuthorityHystrix.class )
//public interface ResourceAuthorityApi {
//
////    @RequestMapping(value = "/auth/hasPermission")
////    boolean hasPermission(@RequestBody HttpServletRequest request, Authentication authentication);
//}
