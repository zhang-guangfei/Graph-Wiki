package com.sales.ops.serviceimpl.event.v3.order.service;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.service.order.BaseCustomerOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * V3事件更新rcv的类，走AoP切面逻辑
 */
@Slf4j
@Service
@AllArgsConstructor
public class RcvdetailService {

    private BaseCustomerOrderService baseCustomerOrderService;

    public void updateRcvdetail(String orderNo, Integer orderItem, Rcvdetail update) throws OpsException {
        baseCustomerOrderService.updateRcvDetail(orderNo, orderItem, update);
    }

}
