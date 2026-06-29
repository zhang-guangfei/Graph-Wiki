package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.service.ExpdetailServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/1/21 10:12
 * @Descripton TODO
 */
@Component
public class ExpdetailServiceFeignHystrix implements FallbackFactory<ExpdetailServiceFeignApi> {
    @Override
    public ExpdetailServiceFeignApi create(Throwable throwable) {
        return new ExpdetailServiceFeignApi() {
            @Override
            public ResultVo<PageInfo<ExpdetailVO>> listExpdetail(ExpdetailRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<ExpdetailVO> findOne(Long id) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updateExpdetail(ExpdetailVO expdetailVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<ExpdetailVO>> findExpDetailByOrderType(ExpdetailRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<ExpdetailVO> listExpdetailByOrderNo(String orderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> callExpExpdetailForGZ() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updateExpdetailOptCodeById(Long id, String optCode) {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
