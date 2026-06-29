package com.smc.smccloud.service;

import com.smc.smccloud.model.ordersales.OrderDlvDataDO;

/**
 * @Author lyc
 * @Date 2023/9/12 9:53
 * @Descripton TODO
 */
public interface OrderDlvDataService {
    /**
     * 保存订单收货信息
     *
     * @param data 订单收货信息
     * @return boolean
     */
    boolean saveOrderDlvData(OrderDlvDataDO data);
}
