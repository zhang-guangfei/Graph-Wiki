package com.smc.smccloud.service;

import com.smc.smccloud.service.hystrix.SsoTokenVerifyServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "auth-service", contextId = "verify", path = "/", fallback = SsoTokenVerifyServiceHystrix.class)
public interface SsoTokenVerifyService {

    @RequestMapping(value = "/authen/verify", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean verify(@RequestBody Map<String, String> map);

}
