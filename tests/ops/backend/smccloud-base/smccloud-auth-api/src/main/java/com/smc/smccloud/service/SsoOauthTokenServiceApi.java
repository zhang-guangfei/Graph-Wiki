package com.smc.smccloud.service;

import com.smc.smccloud.MyFeginConfig.MyFeginConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "auth-service", contextId = "token", configuration = MyFeginConfig.class)
public interface SsoOauthTokenServiceApi {
    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    public OAuth2AccessToken token(@RequestHeader(value = "Authorization") String authorization,
                                   @RequestParam("usernumber") String usernumber, @RequestParam("token") String token,
                                   @RequestParam("grant_type") String grant_type);

}
