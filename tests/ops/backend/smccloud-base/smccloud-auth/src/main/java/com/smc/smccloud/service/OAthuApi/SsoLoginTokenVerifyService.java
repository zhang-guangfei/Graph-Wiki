package com.smc.smccloud.service.OAthuApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * C14717 bugid：14930  2024/8/14
 */
@Service
@FeignClient(name = "ssoLogin" ,url="${sso.url}", path = "/")
public interface SsoLoginTokenVerifyService {

    @RequestMapping(value = "/authen/verify", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean verify(@RequestBody Map<String, String> map);

}
