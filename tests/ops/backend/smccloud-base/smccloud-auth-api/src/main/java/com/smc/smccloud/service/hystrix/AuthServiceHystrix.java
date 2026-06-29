package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.service.AuthServiceApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHystrix implements FallbackFactory<AuthServiceApi> {
    @Override
    public AuthServiceApi create(Throwable throwable) {
        return new AuthServiceApi() {
            @Override
            public Boolean hasPermission(String url) {
                return false;
            }

            @Override
            public Boolean hasFuncPermission(String authName,String loginUserNo) {
                return false;
            }
        };
    }
}
