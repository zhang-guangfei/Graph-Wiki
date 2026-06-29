package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.OrderDeleteReturn;
import com.smc.smccloud.model.ordermodify.ApproveOrderModifyDTO;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.service.hystrix.OrderModifyServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2022/1/13 16:14
 */
@FeignClient(name = "order-service",
        contextId = "ordermodify",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = OrderModifyServiceFeignHystrix.class)
public interface OrderModifyServiceFeignApi {

//    /**
//     * 订单修改申请接口
//     */
//    @RequestMapping(value = "/order/modify/applyOrderModify", method = RequestMethod.POST)
//    ResultVo<List<Long>> applyOrderModify(@RequestBody List<OrderModifyVO> list);

    /**
     * 门户取消订单
     */
    @RequestMapping(value = "/order/modify/applyOrderCancel", method = RequestMethod.POST)
    ResultVo<List<OrderDeleteReturn>> applyOrderCancel(@RequestBody List<OrderModifyVO> list);


//    @RequestMapping(value = "/order/modify/modifyOrderEprice", method = RequestMethod.GET)
//    ResultVo<String> modifyOrderEprice(@RequestParam("fromTime") Date fromTime);

    @RequestMapping(value = "/order/modify/calcImportLotEPrice", method = RequestMethod.GET)
    ResultVo<String> calcImportLotEPrice();

    @RequestMapping(value = "/order/modify/upTaskInfoByBatchNo", method = RequestMethod.POST)
    ResultVo<String> upTaskInfoByBatchNo(@RequestBody UpTaskInfoVO param);

    // 13191bug 订单修改删单优化 增加定时任务 若订单状态为已发货或者已开票，将该删单申请自动否决
    @RequestMapping(value = "/order/modify/autoHandNotCancelData", method = RequestMethod.GET)
    ResultVo<String> autoHandNotCancelData();

    @RequestMapping(value = "/order/modify/calcExportLotEPrice", method = RequestMethod.GET)
    ResultVo<String> calcExportLotEPrice();
}
