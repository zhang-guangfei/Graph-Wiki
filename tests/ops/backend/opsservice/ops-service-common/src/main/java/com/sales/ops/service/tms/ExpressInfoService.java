package com.sales.ops.service.tms;

import com.sales.ops.dto.order.ExpressInfoDto;

/**
 * @author C12961
 * @date 2023/1/6 10:46
 */
public interface ExpressInfoService {
    ExpressInfoDto getExpressInfo(String courierNo, String type);
}
