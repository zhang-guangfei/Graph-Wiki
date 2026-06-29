package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.Model.SaleEmployeeVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.UserInfoServiceApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author lyc
 * @Date 2022/4/8 10:57
 * @Descripton TODO
 */
@Component
public class UserInfoServiceApiHystrix implements FallbackFactory<UserInfoServiceApi> {
    @Override
    public UserInfoServiceApi create(Throwable throwable) {
        return new UserInfoServiceApi() {
            @Override
            public ResultVo<SaleEmployeeVO> findUserInfo(String no) {
                return null;
            }
        };
    }
}
