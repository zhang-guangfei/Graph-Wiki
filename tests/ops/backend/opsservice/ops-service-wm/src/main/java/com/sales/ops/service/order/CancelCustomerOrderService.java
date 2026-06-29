package com.sales.ops.service.order;

import com.sales.ops.common.errorEnum.OrderCancelCodeEnum;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.dto.order.AutoOrderChangeToInitStatusDto;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.CancelInputForOrderDto;
import com.sales.ops.dto.order.OrderChangeToInitStatusDto;

/**
 * @author C12961
 * @date 2023/2/20 15:57
 */
public interface CancelCustomerOrderService {


    int validatePurchaseInfoToAutoToInitStatus(Rcvdetail rcvdetail, AutoOrderChangeToInitStatusDto input, OrderChangeToInitStatusDto output) throws OpsException;

    OrderCancelCodeEnum autoCancelRcvOrder(CancelForOrderDto dto) throws OpsException;

    OrderCancelCodeEnum cancelRcvOrder(CancelInputForOrderDto cancelInputDto) throws OpsException;

    boolean enableCancel(Rcvdetail rcvdetail);
}
