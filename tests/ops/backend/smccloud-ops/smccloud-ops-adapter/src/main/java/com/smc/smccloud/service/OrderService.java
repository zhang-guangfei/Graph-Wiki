package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;

import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-02-14 16:58
 * Description:
 */
public interface OrderService {

    /**
     * 处理门户订单取消消息
     *
     * @param dto 订单取消消息
     * @return 处理结果
     */
    List<OrderCancelResult> handleOrderCancelMQ(OrderCancelDTO dto);

    /**
     * 发送订单取消结果到返回结果消息队列
     *
     * @param resultList 订单取消结果
     * @return boolean
     */
    ResultVo<Boolean> sendOrderCancelReturnMessage(List<OrderCancelResult> resultList);

    /**
     * 处理门户接单消息
     *
     * @param orderList 接单信息
     * @param workTime  发单时间
     * @return 处理结果
     */
    List<AdapterOrderResult> handleOrderCreateMQ(List<AdapterOrder> orderList, Date workTime);


    // 订单修改队列
    AdapterReturn handleOrderEditMQ(OrderDeliveryModel orderDelivery);

    /***
     * 更新货期：判断是否可更新及执行更新
     */
    List<AdapterReturn> handleOrderDlvDateModifyMQ(List<OrderDelivery> orderDeliveryDate);

    List<OrderModifyVO> listOrderModify();

}
