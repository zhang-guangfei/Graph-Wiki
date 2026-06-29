package com.smc.ops.delivery.inquiry.service;

import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * 采购催促适配器
 */
public interface InquiryAdapterService {

    // 催促发送接口
    ResultVo<String> inquirySend(List<OpsInquiryPoHandle> opsInquiryPoHandle);

    // 催促回调接口
    ResultVo<List<OpsInquiryPoHandle>>  inquiryCallBack(List<OpsInquiryPoHandle> callParams);

    // 增量获取催促回调，获取指定id之后的数据
    ResultVo<List<OpsInquiryPoHandle>>  inquiryCallBackIncrement(String callbackIncrementParam);


}
