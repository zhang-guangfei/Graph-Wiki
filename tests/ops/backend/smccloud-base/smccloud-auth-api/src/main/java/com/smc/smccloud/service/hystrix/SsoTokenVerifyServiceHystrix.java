package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.service.SsoTokenVerifyService;
import org.springframework.stereotype.Service;

import java.util.Map;

//@Component
//public class SsoTokenVerifyServiceHystrix implements FallbackFactory<SsoTokenVerifyService> {
//
//    @Override
//    public SsoTokenVerifyService create(Throwable throwable) {
//        return new SsoTokenVerifyService() {
//            @Override
//            public Boolean verify(Map<String, String> map) {
//                return null;
//            }
//        };
//    }
//}
@Service
public class SsoTokenVerifyServiceHystrix implements SsoTokenVerifyService {

    @Override
    public Boolean verify(Map<String, String> map) {
        return null;
    }
}