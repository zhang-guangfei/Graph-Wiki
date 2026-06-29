package com.smc.smccloud.controller.rpc;

import com.smc.smccloud.service.SsoTokenVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class SsoTokenVerifyServiceClient /*implements SsoTokenVerifyServiceApi*/ {

    @Resource
    private SsoTokenVerifyService ssoTokenVerifyService;

    /*@Resource
    private SsoTokenVerifyServiceApi ssoTokenVerifyServiceApi;*/


   /* @Override
    public Boolean verify(Map<String, String> map) {
        return ssoTokenVerifyService.verify(map);
    }*/

   /* @Override
    public OAuth2AccessToken token(String authorization, String usernumber, String token, String grant_type) {
       return ssoTokenVerifyServiceApi.token(authorization, usernumber, token, grant_type);
    }*/
}
