package com.smc.smccloud.service;

import com.smc.smccloud.model.OrderSales.OrderDlvDataDO;
import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;

/**
 * Author: B90034
 * Date: 2022-01-12 09:02
 * Description:
 */
public interface OrderDlvDataService {

    /**
     * 保存订单收货信息
     *
     * @param data 订单收货信息
     * @return boolean
     */
    boolean saveOrderDlvData(OrderDlvDataDO data);

    /**
     * 根据查询条件查询收货地址信息
     */
    OrderDlvDataVO findOrderDlvInfo(OrderDlvDataVO orderDlvDataVO);

}
