package com.smc.ops.delivery.service.psi;

import com.sales.ops.dto.ips.IpsSignDataBatchPushVO;

/**
 * bug 19413.38 签收数据批量推送服务
 */
public interface IpsReceiveSignDataService {

    /**
     * 批量推送签收信息，发票主表和明细表使用同一个事务
     */
    void batchInsertSignData(IpsSignDataBatchPushVO data);
}
