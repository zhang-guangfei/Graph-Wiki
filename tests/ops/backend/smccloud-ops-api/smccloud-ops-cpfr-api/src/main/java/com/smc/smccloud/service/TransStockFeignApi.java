package com.smc.smccloud.service;


import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.trans.TransOrderCancelDTO;
import com.smc.smccloud.model.trans.TransOrderRequest;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.service.hystrix.TransStockServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

// url="http://10.116.1.15:9000"
@FeignClient(name = "cpfr-service",
        contextId = "cpfr-trans",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = TransStockServiceHystrix.class)
public interface TransStockFeignApi {

    @RequestMapping(value = "/cpfr/trans/transStock", method = RequestMethod.POST)
    ResultVo<String> transStock(@RequestBody List<TransOrderVO> voList);

    @RequestMapping(value = "/cpfr/trans/transStockAll", method = RequestMethod.POST)
    ResultVo<String> transStockAll(@RequestBody List<TransOrderVO> voList);

    @RequestMapping(value = "/cpfr/trans/createTransOrderForMove", method = RequestMethod.POST)
    ResultVo<String> createTransOrderForMove(@RequestBody List<TransOrderDtoForMove> voList);

    @RequestMapping(value = "/cpfr/trans/findTransOrder", method = RequestMethod.POST)
    ResultVo<PageInfo<TransOrderVO>> findTransOrder(@RequestBody TransOrderRequest request);

    @RequestMapping(value = "/cpfr/trans/cancelTransOrder", method = RequestMethod.POST)
    ResultVo<String> cancelTransOrder(@RequestBody TransOrderCancelDTO dto);

    @RequestMapping(value = "/cpfr/trans/exportTransData", method = RequestMethod.POST)
    void exportTransData(@RequestBody TransOrderRequest request);
}
