package com.smc.smccloud.service;


import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.service.hystrix.AuthServiceHystrix;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  发布的时候 去掉 url = "http://10.116.194.236:8800"
 */
@FeignClient(name="auth-service", contextId = "auth-service", fallbackFactory = AuthServiceHystrix.class,
              configuration = AuthFeignAutoConfiguration.class)
public interface AuthServiceApi {

    @RequestMapping(value="/oauth/hasPermission", method= RequestMethod.POST)
    Boolean hasPermission(@RequestParam("url") String url);

    // 判断某角色是否拥有某功能的权限 有->true
    @RequestMapping(value="/oauth/hasFuncPermission", method= RequestMethod.GET)
    Boolean hasFuncPermission(@RequestParam("authName") String authName, @RequestParam("optUserNo") String loginUserNo );
}
