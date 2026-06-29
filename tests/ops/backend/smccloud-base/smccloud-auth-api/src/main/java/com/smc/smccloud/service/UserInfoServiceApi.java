package com.smc.smccloud.service;

import com.smc.smccloud.Model.SaleEmployeeVO;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.hystrix.UserInfoServiceApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lyc
 * @Date 2022/4/8 10:56
 * @Descripton TODO
 */

@FeignClient(name = "auth-service", contextId = "userInfo",
        fallbackFactory = UserInfoServiceApiHystrix.class,
        configuration = AuthFeignAutoConfiguration.class)
public interface UserInfoServiceApi {

    @RequestMapping(value = "/api/user/findUserInfo", method = RequestMethod.GET)
    ResultVo<SaleEmployeeVO> findUserInfo(@RequestParam("no") String no);
}
