package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.service.hystrix.ExpdetailServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/1/21 10:11
 * @Descripton
 */

@FeignClient(name = "order-service",
        contextId = "expdetail",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = ExpdetailServiceFeignHystrix.class)
public interface ExpdetailServiceFeignApi {

    @RequestMapping(value = "/order/expdetail/listExpdetail2", method = RequestMethod.POST)
    ResultVo<PageInfo<ExpdetailVO>> listExpdetail(@RequestBody ExpdetailRequest request);

    @RequestMapping(value = "/order/expdetail/findOne", method = RequestMethod.POST)
    ResultVo<ExpdetailVO> findOne(@RequestParam("id") Long id);

    @RequestMapping(value = "/order/expdetail/updateExpdetail", method = RequestMethod.POST)
    ResultVo<String> updateExpdetail(@RequestBody ExpdetailVO expdetailVO);


    @RequestMapping(value = "/order/expdetail/findExpDetailByOrderType", method = RequestMethod.POST)
    ResultVo<List<ExpdetailVO>> findExpDetailByOrderType(@RequestBody ExpdetailRequest request);

    /**
     * 通过完整订单号获取发货信息
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/order/expdetail/listExpdetailByOrderNo", method = RequestMethod.GET)
    ResultVo<ExpdetailVO> listExpdetailByOrderNo(@RequestParam(value = "orderNo") String orderNo);

    @RequestMapping(value = "/order/expdetail/callExpExpdetailForGZ", method = RequestMethod.GET)
    ResultVo<String> callExpExpdetailForGZ();

    @RequestMapping(value = "/order/expdetail/updateExpdetailOptCodeById", method = RequestMethod.GET)
    ResultVo<String> updateExpdetailOptCodeById(@RequestParam("id") Long id, @RequestParam("optCode") String optCode);
}
