package com.sales.ops.api.service.tms;

import com.sales.ops.api.dto.tms.BillingDataDto;
import com.sales.ops.api.dto.tms.CarrierInfoDto;
import com.sales.ops.api.dto.tms.LogisticsRoutingInfoDto;
import com.sales.ops.api.dto.wms.OpsDoConfirmDto;
import com.sales.ops.api.dto.wms.OpsRoConfirmDto;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.CommonResult;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：接收Tms的数据
 * @date ：Created in 2021/10/27 16:53
 */
public interface OpsRecApiFromTmsService {

    //7.1.	承运商信息回传
    CommonResult carrierInfo(CarrierInfoDto param) throws OpsException;

    //7.3.	TMS推送物流路由信息
    CommonResult logisticsRoutingInfo(LogisticsRoutingInfoDto param) throws OpsException;

    // 7.4.	TMS推送账单数据
    CommonResult billingData(BillingDataDto param) throws OpsException;
}
