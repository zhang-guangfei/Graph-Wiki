package com.sales.ops.feign.inquiry;


import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 样品出库数据 抽取到sample_bal 表中
 */
@FeignClient(name = "delivery-server", contextId = "inquiryFeignServer")
public interface InquiryJobFeignApi {

    /**
     * 催促订单发送
     */
    @RequestMapping(value = "/do/inquiry/task/purchaseSend", method = RequestMethod.POST)
    ResultVo<String> purchaseSendJob();

    /**
     * 日次催促获取回复信息
     */
    @RequestMapping(value = "/do/inquiry/task/purchaseReply", method = RequestMethod.POST)
    ResultVo<String> purchaseReplyJob();


    /**
     * 增量获取回复信息
     */
    @RequestMapping(value = "/do/inquiry/task/purchaseReplyIncrement", method = RequestMethod.POST)
    ResultVo<String> purchaseReplyIncrementJob();

}
