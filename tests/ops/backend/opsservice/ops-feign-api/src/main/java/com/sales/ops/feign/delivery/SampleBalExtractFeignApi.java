package com.sales.ops.feign.delivery;


import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 样品出库数据 抽取到sample_bal 表中
 */
@FeignClient(name = "delivery-server", contextId = "sampleBalFeignServer")
public interface SampleBalExtractFeignApi {

    /**
     * 不拆分或数量拆分数据抽取
     */
    @RequestMapping(value = "/do/samplebal/unprodextract", method = RequestMethod.GET)
    CommonResult<String> unprodextract();

    /**
     * 型号拆分订单抽取
     */
    @RequestMapping(value = "/do/samplebal/prodextract", method = RequestMethod.GET)
    CommonResult<String> prodextract();


}
