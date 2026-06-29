package com.smc.ops.delivery.inquiry.controller;

import com.smc.ops.delivery.inquiry.service.InquiryTaskService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 催促定时任务调度实体类
 * @author: B91717
 * @time: 2023/12/23 8:54
 */
@RestController
@CrossOrigin
@RequestMapping("/inquiry/task")
public class InquiryTaskCotroller {

    @Resource
    private InquiryTaskService inquiryTaskService;

    // 采购催促订单发送
    @RequestMapping(value = "/purchaseSend",method = RequestMethod.POST)
    public ResultVo<String> purchaseSendTask(){
        return inquiryTaskService.execPurchaseSendTask();
    }

    @RequestMapping(value = "/purchaseReply",method = RequestMethod.POST)
    public ResultVo<String> purchaseReplyTask(){
        try {
            return inquiryTaskService.execPurchaseReplyTask();
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }


    @RequestMapping(value = "/purchaseReplyIncrement",method = RequestMethod.POST)
    public ResultVo<String> purchaseReplyIncrementTask(){
        try {
            return inquiryTaskService.execPurchaseReplyTaskIncrement();
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

}
