package com.sales.ops.api.service.impl.tms;

import com.sales.ops.api.dto.tms.BillingDataDto;
import com.sales.ops.api.dto.tms.CarrierInfoDto;
import com.sales.ops.api.dto.tms.LogisticsRoutingInfoDto;
import com.sales.ops.api.service.tms.OpsRecApiFromTmsService;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops接收 tms数据
 * @date ：Created in 2021/10/27 16:54
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsRecApiFromTmsServiceImpl implements OpsRecApiFromTmsService {

    //7.1.	承运商信息回传
    @Override
    public CommonResult carrierInfo(CarrierInfoDto param) throws OpsException {
        return null;
    }

    //7.3.	TMS推送物流路由信息
    @Override
    public CommonResult logisticsRoutingInfo(LogisticsRoutingInfoDto param) throws OpsException {
        return null;
    }

    // 7.4.	TMS推送账单数据
    @Override
    public CommonResult billingData(BillingDataDto param) throws OpsException {
        return null;
    }
}
