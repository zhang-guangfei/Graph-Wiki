package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.prestock.ProductBomDetailVO;
import com.smc.smccloud.model.prestock.ProductBomVO;
import com.smc.smccloud.service.ProductBomFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author edp04
 * @title: ProductBomFeignApiHystrix
 * @date 2022/06/10 17:23
 */
@Component
public class ProductBomFeignApiHystrix implements FallbackFactory<ProductBomFeignApi> {
    @Override
    public ProductBomFeignApi create(Throwable throwable) {
        return new ProductBomFeignApi() {

            @Override
            public ResultVo<List<ProductBomDetailVO>> findSplittableDetailByModelNo(String modelNo) {
                return ResultVo.failure("服务异常,降级处理");
            }

            @Override
            public ResultVo<Boolean> isCanSplit(String modelNo) {
                return ResultVo.failure("服务异常,降级处理");
            }
        };
    }
}
