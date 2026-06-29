package com.smc.ops.delivery.inquiry.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

/**
 * 采购催促适配器
 */
public interface InquiryTaskService {


    // 采购催促，待处理清单发送
    ResultVo<String> execPurchaseSendTask();

    // 日次全量轮询全部处理中的催促单信息
    ResultVo<String> execPurchaseReplyTask() throws Exception;

    // 5min/次 增量轮询供应商中间表的处理结果
    ResultVo<String> execPurchaseReplyTaskIncrement() throws Exception;



}
