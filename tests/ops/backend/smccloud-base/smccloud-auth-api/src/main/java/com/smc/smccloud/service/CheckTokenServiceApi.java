package com.smc.smccloud.service;

import com.smc.smccloud.service.hystrix.CheckTokenFallBackServiceApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "auth-service", contextId = "check-token", fallback = CheckTokenFallBackServiceApiHystrix.class)
public interface CheckTokenServiceApi {

    @RequestMapping(value = "/oauth/check_token", method = RequestMethod.GET)
    public OAuth2AccessToken checkToken(@RequestParam("token") String token);
}
