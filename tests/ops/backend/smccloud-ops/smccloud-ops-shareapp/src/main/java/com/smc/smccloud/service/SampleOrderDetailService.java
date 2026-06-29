package com.smc.smccloud.service;

import com.smc.smccloud.model.sampleorder.SamplebalDO;
import com.smc.smccloud.model.sampleorder.SampleorderDetailDO;

/**
 * @Author lyc
 * @Date 2022/7/23 12:13
 * @Descripton TODO
 */
public interface SampleOrderDetailService {

    // 根据结转类型获取发票前缀,生成发票号
    String getInvoiceNoByCostType(SamplebalDO samplebalDO);

}
