package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.prestock.ProductBomDetailVO;
import com.smc.smccloud.model.prestock.ProductBomVO;
import com.smc.smccloud.service.hystrix.ProductBomFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author edp04
 * @title: ProductBomFeignApi
 * @date 2022/06/10 17:23
 */
@FeignClient(name = "shareapp-service",
        contextId = "product-bom",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = ProductBomFeignApiHystrix.class)
public interface ProductBomFeignApi {


    /**
     * 根据型号查出该型号的可拆分型号信息
     * @param modelNo 型号
     * @return List
     */
    @RequestMapping(value = "/shareapp/product/findSplittableDetailByModelNo", method = RequestMethod.GET)
    ResultVo<List<ProductBomDetailVO>> findSplittableDetailByModelNo(@RequestParam("modelNo") String modelNo);

    @RequestMapping(value = "/shareapp/product/isCanSplit", method = RequestMethod.GET)
    ResultVo<Boolean> isCanSplit(@RequestParam("modelNo") String modelNo);
}
