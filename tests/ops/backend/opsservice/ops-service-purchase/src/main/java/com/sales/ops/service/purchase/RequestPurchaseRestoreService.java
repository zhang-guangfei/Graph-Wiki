package com.sales.ops.service.purchase;

/**
 * @author B91717
 */
public interface RequestPurchaseRestoreService {

    /**
     * 拦截订单自动还原，定时任务
     */
    public String interceptRestore() throws Exception;
}
