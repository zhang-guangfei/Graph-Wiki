package com.sales.ops.serviceimpl.event.v3.deliveryPush;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.ips.IpsReceiveDeliveryInfoFromAllVO;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.util.CommonResult;

public interface OrderDeliveryPushEventService {
    void handleOrderCancel(String orderNo, Integer orderItem, CancelForOrderDto cancelDto) throws OpsException;

    void handleAllotFailure(String orderNo, Integer orderItem, IpsReceiveDeliveryInfoFromAllVO data) throws OpsException;

    void handleBeforeAllot(String orderNo, Integer orderItem, IpsReceiveDeliveryInfoFromAllVO vo) throws OpsException;

    void handleNotAllot(String orderNo, Integer orderItem, CommonResult<String> errorMsg) throws OpsException;

    void handleAfterAllot(String orderNo, Integer orderItem) throws OpsException;

    void handleEsDeliveryDate(String orderNo, Integer orderItem) throws OpsException;

    void handleFactDate(String orderNo, Integer orderItem) throws OpsException;


    void handleInvoiced(String orderNo, Integer orderItem) throws OpsException;


    void handlePoInvoiceConfirm(String orderNo, Integer orderItem, OpsPurchaseinvoice po, String operator) throws OpsException;

    void handlePoInvoiceSign(String orderNo, Integer orderItem, OpsPurchaseinvoice po, String operator) throws OpsException;
}
