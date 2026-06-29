package com.sales.ops.service.order.state;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.model.orderstate.OrderStateVO;

/**
 * @author C12961
 * @date 2022/9/28 14:06
 */
public interface OrderStateService {
    CommonResult<String> addOrderState(OrderStateVO orderStateVO) throws OpsException;

    String insertOrderStateLog(OrderStateVO vo);

    void sendOrderStateForCancelPurchaseByPoReset(OpsPurchaseorder po);

    void sendOrderStateForInterceptPurchase(OpsRequestpurchase dto);

    void sendOrderStateForCreateRequestPurchase(OpsRequestpurchase po);

    void sendOrderStateForSendPurchase(OpsPurchaseorder po);

    void sendOrderStateForFinishPurchase(OpsPurchaseorder opsPurchaseorder);

    void sendOrderStateForFinishRequestPurchase(OpsRequestpurchase requestPurchase);

    void sendOrderStateForUpdateRcvStatus(Rcvdetail rcvdetail);

    void sendOrderStateForUpdateRcvDlvDate(Rcvdetail rcvdetail,String updateUser);

    void sendOrderStateForCancelCustomerOrder(CancelForOrderDto cancelDto, Rcvdetail rcvdetail, Rcvmaster rcvmaster);

    void sendOrderStateForCancelPurchase(OpsPurchaseorder opsPurchaseorder);

    void sendOrderStateForCancelRequestPurchase(OpsRequestpurchase po);
}
