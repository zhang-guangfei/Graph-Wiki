package com.sales.ops.api.service.tms;

import com.sales.ops.api.dto.tms.InquiryFreightCalculationDto;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.CommonResult;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交数据到wms
 * @date ：Created in 2021/10/27 16:52
 */
public interface OpsPostApiToTmsService {

    CommonResult inquiryFreightCalculation(InquiryFreightCalculationDto param) throws OpsException;

}
