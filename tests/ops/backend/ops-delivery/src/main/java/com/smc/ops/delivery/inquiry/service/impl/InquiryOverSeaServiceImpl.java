package com.smc.ops.delivery.inquiry.service.impl;

import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.smc.ops.delivery.inquiry.service.InquiryAdapterService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购催促适配器实现
 */
@Service
public class InquiryOverSeaServiceImpl implements InquiryAdapterService {

    @Override
    public ResultVo<String> inquirySend(List<OpsInquiryPoHandle> opsInquiryPoHandle) {
        System.out.println("海外三国问询发送");
        return ResultVo.success("海外三国问询发送");
    }

    @Override
    public ResultVo<List<OpsInquiryPoHandle>> inquiryCallBack(List<OpsInquiryPoHandle> callParams) {
        System.out.println("AS400问询回复接收");
        return ResultVo.success();
    }

    @Override
    public ResultVo<List<OpsInquiryPoHandle>> inquiryCallBackIncrement(String callbackIncrementParam) {
        return null;
    }
}
