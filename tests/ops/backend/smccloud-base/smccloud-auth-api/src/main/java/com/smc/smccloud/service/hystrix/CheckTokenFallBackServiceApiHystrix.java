package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.service.CheckTokenServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckTokenFallBackServiceApiHystrix implements CheckTokenServiceApi {

    @Override
    public OAuth2AccessToken checkToken(String token) {
        log.info("token = 服务降级========================================");
        return null;
    }

}
