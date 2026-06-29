package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import com.smc.smccloud.model.OrderSales.OrderSalesDTO;
import com.smc.smccloud.model.OrderSales.OrderSalesRequest;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderVO;
import com.smc.smccloud.model.adapter.order.AdapterOrderResult;
import com.smc.smccloud.model.receiveorder.OrderCount;

import java.util.List;

public interface OrderSalesService {

    PageInfo<OrderSalesDO> findAll(OrderSalesRequest orderSalesRequest, Page page);

    ResultVo<OrderCount> getOrderCount(OrderSalesRequest deptParam);

    ResultVo<String> addOrderSales(OrderSalesDTO orderSalesDTO);

    /**
     * 接收来自门户的订单信息
     *
     * @param dto 门户订单信息
     * @return 处理结果
     */
    ResultVo<List<AdapterOrderResult>> addOrderSalesFromSMS(OrderSalesDTO dto);


    /**
     * 更新到已处理状态
     * @return
     */
    int updateOrderSalesToProcessedStatus(String rorderNo, int rorderItemNo);

    /**
     * 从ips接入工厂订单
     */
    ResultVo<String> receiveIPSOrder(List<String> id);


    /**
     * 根据接入订单状态 修改接入中国制造订单状态
     */
    ResultVo<String> updateCNMadeOrderStatus();

    void pushEvent(VSalesManuorderVO item, String remark);

}

